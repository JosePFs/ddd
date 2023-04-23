package com.example.transactions.application.usecase.createtransaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;

import com.example.transactions.domain.exception.MandatoryParameterException;
import com.example.transactions.domain.model.transaction.Iban;
import com.example.transactions.domain.model.transaction.Reference;
import com.example.transactions.domain.model.transaction.Transaction;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateTransactionParamsTest {

  private static final String REFERENCE = "reference";

  private static final double AMOUNT = 10.0;

  private static final double FEE = 1.0;

  private static final String DESCRIPTION = "description";

  private static final long DATE = 1681087396L;

  private static final Iban IBAN = Iban.create("iban");

  @Nested
  class Build {

    @Nested
    class GivenANullIban {

      @Test
      void shouldThrowAnError() {
        assertThrows(MandatoryParameterException.class, () -> createTransactionParamsBuilder().iban(null).build());
      }
    }

    @Nested
    class GivenANullAmount {

      @Test
      void shouldThrowAnError() {
        assertThrows(MandatoryParameterException.class, () -> createTransactionParamsBuilder().amount(null).build());
      }
    }
  }

  @Nested
  class ToTransaction {

    @Nested
    class GivenCreateAParams {

      private final CreateTransactionParams createTransactionParams = createTransactionParamsBuilder().build();

      @Test
      void shouldReturnATransaction() {
        assertThat(createTransactionParams.toTransaction()).isEqualTo(expectedTransaction());
      }
    }

    private Transaction expectedTransaction() {
      return Transaction.builder()
          .iban(IBAN)
          .reference(Reference.create(REFERENCE))
          .amount(AMOUNT)
          .fee(FEE)
          .description(DESCRIPTION)
          .date(Instant.ofEpochMilli(DATE))
          .build();
    }
  }

  private CreateTransactionParams.CreateTransactionParamsBuilder createTransactionParamsBuilder() {
    return CreateTransactionParams.builder()
        .iban(IBAN.getValue())
        .reference(REFERENCE)
        .amount(AMOUNT)
        .fee(FEE)
        .description(DESCRIPTION)
        .date(DATE);
  }
}
