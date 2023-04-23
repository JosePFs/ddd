package com.example.transactions.domain.model.transaction;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;

import com.example.transactions.domain.exception.InvalidParameterException;
import com.example.transactions.domain.exception.InvalidTransactionException;
import com.example.transactions.domain.exception.MandatoryParameterException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class TransactionTest {

  private static final Iban IBAN = Iban.create("iban");

  @Nested
  class Build {

    @Nested
    class GivenANullReference {

      @Test
      void shouldThrowAnException() {
        assertThrows(MandatoryParameterException.class, () -> builder().reference(null).build());
      }
    }

    @Nested
    class GivenANullAmount {

      @Test
      void shouldThrowAnException() {
        assertThrows(MandatoryParameterException.class, () -> builder().amount(null).build());
      }
    }

    @Nested
    class GivenANullIban {

      @Test
      void shouldThrowAnException() {
        assertThrows(MandatoryParameterException.class, () -> builder().iban(null).build());
      }
    }

    @Nested
    class GivenANegativeFee {

      @Test
      void shouldThrowAnException() {
        assertThrows(InvalidParameterException.class, () -> builder().fee(-1.0).build());
      }
    }

    @Nested
    class ApplyToAccount {

      private final Account account = Account.builder().iban(IBAN).balance(1.0).build();

      @Nested
      class GivenAPositiveBalance {

        @Test
        void shouldReturnAnUpdatedAccount() {
          Transaction transaction = builder().amount(10.0).build();

          transaction.applyToAccount(account);
        }
      }

      @Nested
      class GivenANegativeBalance {

        @Test
        void shouldThrowAnException() {
          Transaction transaction = builder().amount(-10.0).build();

          assertThrows(InvalidTransactionException.class, () -> transaction.applyToAccount(account));
        }
      }
    }
  }

  private Transaction.Builder builder() {
    return Transaction.builder()
        .reference(Reference.create("reference"))
        .date(Instant.ofEpochMilli(1L))
        .amount(10.0)
        .fee(1.0)
        .description("description")
        .iban(IBAN);
  }
}
