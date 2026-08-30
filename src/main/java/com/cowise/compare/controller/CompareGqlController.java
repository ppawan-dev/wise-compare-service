package com.cowise.compare.controller;

import com.cowise.compare.service.CompareService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.cowise.compare.dto.ComparisonResponse;

@Controller
@CrossOrigin(value = "http://localhost:5173/")
public class CompareGqlController {

  private final CompareService compareService;

  public CompareGqlController(CompareService compareService) {
    this.compareService = compareService;
  }

  @QueryMapping
  public ComparisonResponse compare(@Argument int amount, @Argument String sourceCurrency,
      @Argument String targetCurrency) {
    ResponseEntity<ComparisonResponse> response = compareService.compare(amount, sourceCurrency, targetCurrency);
    return response.getBody();
  }
}
