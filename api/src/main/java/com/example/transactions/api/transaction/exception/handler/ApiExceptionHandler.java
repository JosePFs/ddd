package com.example.transactions.api.transaction.exception.handler;

import com.example.transactions.api.transaction.exception.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleAllExceptions(Exception exception) {
    log.debug(exception.getMessage(), exception);
    ApiError error = ApiError.builder()
        .code(HttpStatus.BAD_REQUEST.value())
        .build();
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }
}
