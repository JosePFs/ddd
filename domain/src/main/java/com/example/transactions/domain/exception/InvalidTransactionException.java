package com.example.transactions.domain.exception;

public class InvalidTransactionException extends TransactionsException {

  public InvalidTransactionException(String message) {
    super(message);
  }
}
