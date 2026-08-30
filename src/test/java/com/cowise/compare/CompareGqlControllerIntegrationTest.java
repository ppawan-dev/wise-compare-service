package com.cowise.compare;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.cowise.compare.controller.CompareGqlController;
import com.cowise.compare.dto.ComparisonResponse;
import com.cowise.compare.service.CompareService;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class CompareGqlControllerIntegrationTest {

  @Test
  void graphqlCompareQuery_shouldReturnComparisonResponse() {
    CompareService compareService = mock(CompareService.class);
    ComparisonResponse response = new ComparisonResponse(
        new BigDecimal("100.00"),
        "SEND",
        "GB",
        List.of("BANK_TRANSFER", "PISP"),
        List.of(),
        "US",
        "USD",
        "GB",
        "EUR");

    when(compareService.compare(100, "USD", "EUR")).thenReturn(ResponseEntity.ok(response));

    CompareGqlController controller = new CompareGqlController(compareService);
    ComparisonResponse result = controller.compare(100, "USD", "EUR");

    assertEquals(response, result);
    assertEquals("USD", result.sourceCurrency());
    assertEquals("EUR", result.targetCurrency());
  }
}
