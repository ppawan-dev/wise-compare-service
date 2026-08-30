package com.cowise.compare.controller;

import org.springframework.web.bind.annotation.RestController;

import com.cowise.compare.dto.ComparisonResponse;
import com.cowise.compare.service.CompareService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin(value = "http://localhost:5173/")
public class CompareController {

  private final CompareService compareService;

  public CompareController(CompareService compareService) {
    this.compareService = compareService;
  }

  @GetMapping("/compare")
  public ResponseEntity<ComparisonResponse> compare(
      @RequestParam("sendAmount") int amount,
      @RequestParam String sourceCurrency,
      @RequestParam String targetCurrency) {
    return compareService.compare(amount, sourceCurrency, targetCurrency);
  }
}