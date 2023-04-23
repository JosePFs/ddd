package com.example.transactions.application.usecase.searchtransactions;

import java.util.Optional;

import com.example.transactions.domain.model.transaction.Iban;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Builder
public class SearchTransactionsParams {

  private static final String DEFAULT_DIRECTION = "ASC";

  private final Iban iban;

  private final String sort;

  public SearchTransactionsParams(Iban iban, String sort) {
    this.iban = iban;
    this.sort = sort;
  }

  public Iban getIban() {
    return iban;
  }

  public String getSort() {
    return Optional.ofNullable(sort).orElse(DEFAULT_DIRECTION).toUpperCase();
  }
}
