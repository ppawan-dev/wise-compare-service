package com.cowise.compare.controller;

import org.springframework.web.bind.annotation.RestController;

import com.cowise.compare.dto.ComparisonResponse;
import com.cowise.compare.service.CompareService;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@Validated
@CrossOrigin(value = "http://localhost:5173/")
public class CompareController {

  private final CompareService compareService;

  public CompareController(CompareService compareService) {
    this.compareService = compareService;
  }

  @GetMapping("/compare")
  public ResponseEntity<ComparisonResponse> compare(
      @RequestParam("sendAmount") @Min(value = 1, message = "Amount must be greater than zero") int amount,
      @RequestParam @NotBlank(message = "Source currency is required") @Pattern(regexp = "^[A-Z]{3}$", message = "Source currency must be a 3-letter ISO code") String sourceCurrency,
      @RequestParam @NotBlank(message = "Target currency is required") @Pattern(regexp = "^[A-Z]{3}$", message = "Target currency must be a 3-letter ISO code") String targetCurrency) {
    return ResponseEntity.ok(compareService.compare(amount, sourceCurrency, targetCurrency));
  }
}