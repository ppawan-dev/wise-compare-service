# Wise Compare Service

A Spring Boot service that compares money transfer offers across currencies and providers. The project exposes the same comparison capability through two controller approaches:

- REST API controller
- GraphQL controller

## Project overview

The service uses:

- Spring Boot
- Spring Web
- Spring GraphQL
- Java 17+
- Maven Wrapper

The main comparison logic is handled by `CompareService`, and both controller styles call the same service method for consistent results.

## Run the application

From the project root:

```bash
./mvnw spring-boot:run
```

The application runs with:

- REST base path: `http://localhost:8082/api`
- GraphQL endpoint: `http://localhost:8082/api/graphql`
- GraphiQL UI: `http://localhost:8082/api/graphiql`

---

## 1) REST controller approach

The REST implementation is in `CompareController` and is annotated with `@RestController`.

### Endpoint

```http
GET /api/compare?sendAmount=1000&sourceCurrency=USD&targetCurrency=EUR
```

### Example request

```bash
curl "http://localhost:8082/api/compare?sendAmount=1000&sourceCurrency=USD&targetCurrency=EUR"
```

### Response shape

The endpoint returns a `ComparisonResponse` JSON object containing comparison data such as:

- source and target currency
- provider details
- quotes and rates
- logos and delivery estimates

This is the traditional HTTP-style controller approach, ideal for clients that expect standard REST endpoints and JSON responses.

![Using RESTful](assets\rest.png)

---

## 2) GraphQL controller approach

The GraphQL implementation is in `CompareGqlController` and is annotated with `@Controller` plus Spring GraphQL mapping annotations.

### GraphQL schema

The schema is defined in `src/main/resources/schema.graphqls` and includes the query:

```graphql
type Query {
  compare(
    amount: Int!
    sourceCurrency: String!
    targetCurrency: String!
  ): ComparisonResponse!
}
```

### Example query

```graphql
query {
  compare(amount: 1000, sourceCurrency: "USD", targetCurrency: "EUR") {
    amount
    sourceCurrency
    targetCurrency
    providers {
      name
      alias
      type
      quotes {
        rate
        fee
        receivedAmount
      }
    }
  }
}
```

### Example request

```bash
curl -X POST "http://localhost:8082/api/graphql" \
  -H "Content-Type: application/json" \
  --data '{
    "query": "query { compare(amount: 1000, sourceCurrency: \"USD\", targetCurrency: \"EUR\") { amount sourceCurrency targetCurrency providers { name alias type quotes { rate fee receivedAmount } } } }"
  }'
```

This GraphQL approach is useful when you want a single query contract with flexible data selection and a schema-driven API.

![Using GraphQL](assets\gql.png)

---

## Which approach to use?

- Use the REST controller if you want a simple, resource-based endpoint pattern.
- Use the GraphQL controller if you want flexible querying and schema-driven response selection.

Both approaches use the same underlying `CompareService`, so the business logic remains consistent while the API exposure differs.
