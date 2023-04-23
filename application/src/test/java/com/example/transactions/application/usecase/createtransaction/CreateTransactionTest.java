package com.example.transactions.application.usecase.createtransaction;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.Instant;

import com.example.transactions.domain.exception.NotFoundException;
import com.example.transactions.domain.model.transaction.Account;
import com.example.transactions.domain.model.transaction.Iban;
import com.example.transactions.domain.model.transaction.Reference;
import com.example.transactions.domain.model.transaction.Transaction;
import com.example.transactions.domain.model.transaction.repository.TransactionRepository;
import com.example.transactions.domain.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateTransactionTest {

  private static final String REFERENCE = "reference";

  private static final double AMOUNT = 10.0;

  private static final double FEE = 1.0;

  private static final String DESCRIPTION = "description";

  private static final long DATE = 1681087396L;

  private static final Iban IBAN = Iban.create("iban");

  private static final double BALANCE = 10.0;

  @Mock
  private AccountService mockAccountService;

  @Mock
  private TransactionRepository mockTransactionRepository;

  private CreateTransaction createTransaction;

  @BeforeEach
  void setUp() {
    createTransaction = new CreateTransaction(mockAccountService, mockTransactionRepository);
  }

  @Nested
  class Execute {

    @Nested
    class GivenANonExistentAccount {

      @Test
      void shouldNotCreateTransaction() {
        when(mockAccountService.getAccountByIban(IBAN)).thenThrow(new NotFoundException("error"));

        assertThrows(NotFoundException.class, () -> createTransaction.execute(createTransactionParams()));

        verifyNoInteractions(mockTransactionRepository);
      }
    }

    @Nested
    class GivenAnExistentAccount {

      @Nested
      class WhenSuccessUpdatingAccount {

        @Test
        void shouldCreateTransaction() {
          when(mockAccountService.getAccountByIban(IBAN)).thenReturn(account());

          createTransaction.execute(createTransactionParams());

          verify(mockTransactionRepository).create(argThat(transaction -> transaction.equals(transaction())));
          verify(mockAccountService).update(argThat(account -> account.getBalance().equals(19.0)));
        }
      }
    }

    private CreateTransactionParams createTransactionParams() {
      return CreateTransactionParams.builder()
          .iban(IBAN.getValue())
          .reference(REFERENCE)
          .amount(AMOUNT)
          .fee(FEE)
          .description(DESCRIPTION)
          .date(DATE)
          .build();
    }

    private Transaction transaction() {
      return Transaction.builder()
          .iban(IBAN)
          .reference(Reference.create(REFERENCE))
          .amount(AMOUNT)
          .fee(FEE)
          .description(DESCRIPTION)
          .date(Instant.ofEpochMilli(DATE))
          .build();
    }

    private Account account() {
      return Account.builder()
          .iban(IBAN)
          .balance(BALANCE)
          .build();
    }
  }
}
