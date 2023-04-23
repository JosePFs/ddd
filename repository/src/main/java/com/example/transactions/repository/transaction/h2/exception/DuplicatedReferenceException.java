package com.example.transactions.repository.transaction.h2.exception;

public class DuplicatedReferenceException extends RuntimeException {

  public DuplicatedReferenceException(String message) {
    super(message);
  }
}
