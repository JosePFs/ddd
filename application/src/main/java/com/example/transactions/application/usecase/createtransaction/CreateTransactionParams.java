package com.example.transactions.application.usecase.createtransaction;

import java.time.Instant;
import java.util.Optional;

import com.example.transactions.domain.exception.MandatoryParameterException;
import com.example.transactions.domain.model.transaction.Iban;
import com.example.transactions.domain.model.transaction.Reference;
import com.example.transactions.domain.model.transaction.Transaction;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Builder
public class CreateTransactionParams {

  private String iban;

  private Double amount;

  private String reference;

  private Double fee;

  private Long date;

  private String description;

  private CreateTransactionParams(String iban, Double amount, String reference, Double fee, Long date, String description) {
    if (iban == null) {
      throw new MandatoryParameterException("iban");
    }
    if (amount == null) {
      throw new MandatoryParameterException("amount");
    }
    this.iban = iban;
    this.amount = amount;
    this.reference = reference;
    this.fee = fee;
    this.date = date;
    this.description = description;
  }

  public String getIban() {
    return iban;
  }

  public Double getAmount() {
    return amount;
  }

  public Optional<String> getReference() {
    return Optional.ofNullable(reference);
  }

  public Optional<Double> getFee() {
    return Optional.ofNullable(fee);
  }

  public Optional<Long> getDate() {
    return Optional.ofNullable(date);
  }

  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }

  public Transaction toTransaction() {
    return Transaction.builder()
        .amount(this.getAmount())
        .reference(this.getReference().map(Reference::create).orElse(Reference.generate()))
        .fee(this.getFee().orElse(0.0))
        .date(this.getDate().map(Instant::ofEpochMilli).orElse(Instant.now()))
        .description(this.getDescription().orElse(null))
        .iban(Iban.create(this.getIban()))
        .build();
  }
}
