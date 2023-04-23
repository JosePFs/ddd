package com.example.transactions.domain.model.transaction;

import java.util.UUID;

import com.example.transactions.domain.exception.MandatoryParameterException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Reference {

  private final String value;

  private Reference(String value) {
    if (value == null) {
      throw new MandatoryParameterException("value");
    }
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static Reference create(String value) {
    return new Reference(value);
  }

  public static Reference generate() {
    return new Reference(UUID.randomUUID().toString());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof Reference)) {
      return false;
    }

    Reference reference = (Reference) o;
    return new EqualsBuilder().append(getValue(), reference.getValue()).isEquals();
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
