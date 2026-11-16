package com.cowise.compare.controller;

import com.cowise.compare.service.CompareService;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
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
  public ComparisonResponse compare(
      @Argument @Min(value = 1, message = "Amount must be greater than zero") int amount,
      @Argument @NotBlank(message = "Source currency is required") @Pattern(regexp = "^[A-Z]{3}$", message = "Source currency must be a 3-letter ISO code") String sourceCurrency,
      @Argument @NotBlank(message = "Target currency is required") @Pattern(regexp = "^[A-Z]{3}$", message = "Target currency must be a 3-letter ISO code") String targetCurrency) {
    return compareService.compare(amount, sourceCurrency, targetCurrency);
  }
}
