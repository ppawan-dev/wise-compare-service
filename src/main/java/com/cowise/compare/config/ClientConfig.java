package com.cowise.compare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
// @ImportHttpServices(TodoClient.class)
public class ClientConfig {

  @Bean
  public RestClient restClient() {
    return RestClient.builder()
        .baseUrl("https://wise.com")
        .defaultHeader("Accept", "application/json")
        .build();
  }
}
