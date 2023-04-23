package com.example.transactions.domain.exception;

public class InvalidParameterException extends TransactionsException {

  public InvalidParameterException(String message) {
    super(message);
  }
}
