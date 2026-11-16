package com.cowise.compare.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.cowise.compare.dto.ComparisonResponse;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
// import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
// import io.github.resilience4j.retry.annotation.Retry;

@Service
public class CompareService {

  private final Logger logger = LoggerFactory.getLogger(CompareService.class);

  private final RestClient restClient;

  public CompareService(RestClient restClient) {
    this.restClient = restClient;
  }

  @CircuitBreaker(name = "COMPARE_SERVICE")
  // @Retry(name = "COMPARE_SERVICE")
  // @RateLimiter(name = "COMPARE_SERVICE")
  public ComparisonResponse compare(int amount, String sourceCurrency, String targetCurrency) {
    logger.info("Fetching currency compare from {} to {} for Amount: {}", sourceCurrency.toUpperCase(),
        targetCurrency.toUpperCase(), amount);
    return restClient
        .get()
        .uri("/gateway/v4/comparisons?payInMethod=PISP&sendAmount=" + amount + "&sourceCurrency=" + sourceCurrency
            + "&targetCurrency=" + targetCurrency)
        .retrieve()
        .body(ComparisonResponse.class);
  }

  public String compareFallback(Exception e) {
    return e.getMessage();
  }
}
