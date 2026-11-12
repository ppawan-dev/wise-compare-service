# syntax=docker/dockerfile:1.7

# Step 1: Build the application
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /workspace

# Copy only the dependency metadata first so Maven can cache dependencies
COPY pom.xml ./
COPY mvnw ./
COPY .mvn .mvn/
RUN chmod +x mvnw && ./mvnw -B dependency:go-offline

# Copy the rest of the source and build the jar without tests
COPY src ./src
RUN ./mvnw -B clean package -DskipTests

# Step 2: Run the application with a minimal, non-root runtime image
FROM eclipse-temurin:17-jre-jammy

ENV APP_HOME=/app
WORKDIR ${APP_HOME}

RUN apt-get update \
    && apt-get upgrade -y --no-install-recommends \
    && apt-get install -y --no-install-recommends ca-certificates \
    && rm -rf /var/lib/apt/lists/* \
    && groupadd --system spring \
    && useradd --system --gid spring --create-home --home-dir ${APP_HOME} spring

COPY --from=build /workspace/target/*.jar ${APP_HOME}/app.jar
RUN chown -R spring:spring ${APP_HOME}

USER spring
EXPOSE 8082

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]
