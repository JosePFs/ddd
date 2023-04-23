package com.example.transactions.domain.exception;

public class MandatoryParameterException extends TransactionsException {

  public MandatoryParameterException(String message) {
    super(message);
  }
}
