package com.cowise.compare.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cowise.compare.dto.ComparisonResponse;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

class CompareServiceTest {

    @Test
    void compare_shouldCallWiseApiAndReturnBody() {
        RestClient restClient = mock(RestClient.class);
        RestClient.RequestHeadersUriSpec requestHeadersUriSpec = mock(RestClient.RequestHeadersUriSpec.class);
        RestClient.RequestHeadersSpec requestHeadersSpec = mock(RestClient.RequestHeadersSpec.class);
        RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);

        ComparisonResponse expected = new ComparisonResponse(
                new BigDecimal("100.00"),
                "SEND",
                "GB",
                List.of("BANK_TRANSFER", "PISP"),
                List.of(),
                "US",
                "USD",
                "GB",
                "EUR");

        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(
                "/gateway/v4/comparisons?payInMethod=PISP&sendAmount=100&sourceCurrency=USD&targetCurrency=EUR"))
                .thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.toEntity(ComparisonResponse.class)).thenReturn(ResponseEntity.ok(expected));

        CompareService service = new CompareService(restClient);

        ResponseEntity<ComparisonResponse> result = service.compare(100, "USD", "EUR");

        assertEquals(expected, result.getBody());
        verify(restClient).get();
        verify(requestHeadersUriSpec).uri(
                "/gateway/v4/comparisons?payInMethod=PISP&sendAmount=100&sourceCurrency=USD&targetCurrency=EUR");
    }
}
