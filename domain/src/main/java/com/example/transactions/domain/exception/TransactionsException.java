package com.example.transactions.domain.exception;

public class TransactionsException extends RuntimeException {

  private static final long serialVersionUID = 5267504301078704073L;

  public TransactionsException(String message) {
    super(message);
  }
}
