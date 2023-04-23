package com.example.transactions.domain.model.transaction;

import com.example.transactions.domain.exception.MandatoryParameterException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Iban {

  private final String value;

  private Iban(String value) {
    if (value == null) {
      throw new MandatoryParameterException("value");
    }
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static Iban create(String value) {
    return new Iban(value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof Iban)) {
      return false;
    }

    Iban iban = (Iban) o;
    return new EqualsBuilder().append(getValue(), iban.getValue()).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(getValue()).toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("value", value)
        .toString();
  }
}
