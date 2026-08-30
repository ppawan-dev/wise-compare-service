package com.cowise.compare.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
    return error(HttpStatus.NOT_FOUND, ex.getMessage(), request);
  }

  @ExceptionHandler({
      MissingServletRequestParameterException.class,
      MethodArgumentTypeMismatchException.class,
      MethodArgumentNotValidException.class })
  public ResponseEntity<ApiError> handleBadRequest(Exception ex, WebRequest request) {
    return error(HttpStatus.BAD_REQUEST, "The request contains invalid or missing parameters.", request);
  }

  @ExceptionHandler(HttpStatusCodeException.class)
  public ResponseEntity<ApiError> handleUpstreamHttpError(HttpStatusCodeException ex, WebRequest request) {
    HttpStatus status = HttpStatus.resolve(ex.getStatusCode().value());
    HttpStatus responseStatus = status == null ? HttpStatus.BAD_GATEWAY : status;
    return error(responseStatus, "The comparison provider returned an error.", request);
  }

  @ExceptionHandler(RestClientException.class)
  public ResponseEntity<ApiError> handleUpstreamClientError(RestClientException ex, WebRequest request) {
    return error(HttpStatus.BAD_GATEWAY, "The comparison provider could not be reached.", request);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleGlobalException(Exception ex, WebRequest request) {
    return error(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.", request);
  }

  private ResponseEntity<ApiError> error(HttpStatus status, String message, WebRequest request) {
    ApiError body = new ApiError(
        Instant.now(),
        status.value(),
        status.getReasonPhrase(),
        message,
        request.getDescription(false).replace("uri=", ""));
    return ResponseEntity.status(status).body(body);
  }
}
