package com.cowise.compare.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.cowise.compare.dto.ComparisonResponse;

@Service
public class CompareService {

  private final Logger logger = LoggerFactory.getLogger(CompareService.class);

  private final RestClient restClient;

  public CompareService(RestClient restClient) {
    this.restClient = restClient;
  }

  public ResponseEntity<ComparisonResponse> compare(int amount, String sourceCurrency, String targetCurrency) {
    return restClient
        .get()
        .uri("/gateway/v4/comparisons?payInMethod=PISP&sendAmount=" + amount + "&sourceCurrency=" + sourceCurrency
            + "&targetCurrency=" + targetCurrency)
        .retrieve()
        .toEntity(ComparisonResponse.class);
  }
}
