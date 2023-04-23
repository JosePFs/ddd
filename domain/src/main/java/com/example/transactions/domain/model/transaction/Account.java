package com.example.transactions.domain.model.transaction;

import com.example.transactions.domain.exception.InvalidParameterException;
import com.example.transactions.domain.exception.MandatoryParameterException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Account {

  private final Iban iban;

  private final Double balance;

  private Account(Iban iban, Double balance) {
    if (iban == null) {
      throw new MandatoryParameterException("iban");
    }
    if (balance == null || balance < 0.0) {
      throw new InvalidParameterException("Balance can not be less than 0");
    }
    this.iban = iban;
    this.balance = balance;
  }

  public Iban getIban() {
    return iban;
  }

  public Double getBalance() {
    return balance;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof Account)) {
      return false;
    }

    Account account = (Account) o;
    return new EqualsBuilder().append(getIban(), account.getIban()).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(getIban()).append(getBalance())
        .toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("iban", iban)
        .append("balance", balance)
        .toString();
  }

  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(Account account) {
    return new Builder(account);
  }

  public static final class Builder {

    private Iban iban;

    private Double balance;

    private Builder() {
    }

    private Builder(Account account) {
      iban = account.iban;
    }

    public Builder iban(Iban iban) {
      this.iban = iban;
      return this;
    }

    public Builder balance(Double balance) {
      this.balance = balance;
      return this;
    }

    public Account build() {
      return new Account(iban, balance);
    }
  }
}
