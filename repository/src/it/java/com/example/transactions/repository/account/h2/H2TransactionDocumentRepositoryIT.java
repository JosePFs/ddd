package com.example.transactions.repository.account.h2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.time.Instant;

import com.example.transactions.domain.model.transaction.Reference;
import com.example.transactions.domain.model.transaction.Transaction;
import com.example.transactions.domain.model.transaction.repository.TransactionRepository;
import com.example.transactions.repository.config.Application;
import com.example.transactions.repository.transaction.h2.H2TransactionDocumentRepository;
import com.example.transactions.repository.transaction.h2.document.TransactionDocumentDAO;
import com.example.transactions.repository.transaction.h2.document.TransactionDocumentMapper;
import com.example.transactions.repository.transaction.h2.exception.DuplicatedReferenceException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {Application.class, H2TransactionDocumentRepository.class, TransactionDocumentDAO.class,
    TransactionDocumentMapper.class})
public class H2TransactionDocumentRepositoryIT {

  private static final Reference REFERENCE = Reference.create("ref");

  @Autowired
  private TransactionRepository transactionRepository;

  @Nested
  class Save {

    @Nested
    class GivenANewTransaction {

      private final Transaction transaction = transaction();

      @Nested
      class WhenTransactionDoesNotExist {

        @Test
        void shouldCreateANewTransactionDocument() {
          Transaction createdTransaction = transactionRepository.create(transaction);

          assertThat(createdTransaction).isEqualTo(transaction);
        }
      }

      @Nested
      class WhenTransactionReferenceAlreadyExist {

        @Test
        void shouldThrowAnError() {
          transactionRepository.create(transaction(REFERENCE, 10.0));

          assertThrowsExactly(DuplicatedReferenceException.class,
              () -> transactionRepository.create(transaction(REFERENCE, 1.0)));
        }
      }
    }

    private Transaction transaction() {
      return transaction(Reference.generate(), 10.0);
    }

    private Transaction transaction(Reference reference, Double amount) {
      return Transaction.builder()
          .reference(reference)
          .amount(amount)
          .fee(1.0)
          .description("descriptionOne")
          .date(Instant.ofEpochMilli(1681087396L))
          .build();
    }
  }
}
