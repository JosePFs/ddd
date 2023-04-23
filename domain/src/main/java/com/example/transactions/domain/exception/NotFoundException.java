package com.example.transactions.domain.exception;

public class NotFoundException extends TransactionsException {

  public NotFoundException(String message) {
    super(message);
  }
}
