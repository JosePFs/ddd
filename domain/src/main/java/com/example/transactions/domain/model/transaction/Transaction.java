package com.example.transactions.domain.model.transaction;

import java.time.Instant;
import java.util.Optional;

import com.example.transactions.domain.exception.InvalidParameterException;
import com.example.transactions.domain.exception.InvalidTransactionException;
import com.example.transactions.domain.exception.MandatoryParameterException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Transaction {

  private final Reference reference;

  private final Instant date;

  private final Double amount;

  private final Double fee;

  private final String description;

  private final Iban iban;

  private Transaction(Reference reference, Double amount, Iban iban, Instant date, Double fee, String description) {
    if (reference == null) {
      throw new MandatoryParameterException("reference");
    }
    if (amount == null) {
      throw new MandatoryParameterException("amount");
    }
    if (iban == null) {
      throw new MandatoryParameterException("iban");
    }
    if (fee != null && fee < 0) {
      throw new InvalidParameterException("Fee can not be lower than zero");
    }
    this.reference = reference;
    this.date = date;
    this.amount = amount;
    this.fee = fee;
    this.description = description;
    this.iban = iban;
  }

  public Reference getReference() {
    return reference;
  }

  public Instant getDate() {
    return date;
  }

  public Double getAmount() {
    return amount;
  }

  public Double getFee() {
    return fee;
  }

  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }

  public Iban getIban() {
    return iban;
  }

  public Account applyToAccount(Account account) {
    double updatedBalance = account.getBalance() + getAmount() - getFee();
    if (updatedBalance < 0) {
      throw new InvalidTransactionException("Balance can not be less than 0");
    }
    return Account.builder(account).balance(updatedBalance).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof Transaction)) {
      return false;
    }

    Transaction that = (Transaction) o;
    return new EqualsBuilder().append(reference, that.reference).append(date, that.date).append(amount, that.amount).append(fee, that.fee)
        .append(description, that.description).append(iban, that.iban)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(reference).append(date).append(amount).append(fee)
        .append(description).append(iban).toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("reference", reference)
        .append("iban", iban)
        .append("amount", amount)
        .append("date", date)
        .append("fee", fee)
        .append("description", description)
        .toString();
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {

    private Reference reference;

    private Double amount;

    private Iban iban;

    private Instant date;

    private Double fee = 0.0;

    private String description;

    private Builder() {
    }

    public Builder reference(Reference reference) {
      this.reference = reference;
      return this;
    }

    public Builder amount(Double amount) {
      this.amount = amount;
      return this;
    }

    public Builder iban(Iban iban) {
      this.iban = iban;
      return this;
    }

    public Builder date(Instant date) {
      this.date = date;
      return this;
    }

    public Builder fee(Double fee) {
      this.fee = fee;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Transaction build() {
      return new Transaction(reference, amount, iban, date, fee, description);
    }
  }
}
