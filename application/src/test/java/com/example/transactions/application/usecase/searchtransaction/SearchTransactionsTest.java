package com.example.transactions.application.usecase.searchtransaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;

import com.example.transactions.domain.model.common.transformer.Transformer;
import com.example.transactions.application.usecase.searchtransactions.SearchTransactions;
import com.example.transactions.application.usecase.searchtransactions.SearchTransactionsParams;
import com.example.transactions.domain.model.transaction.Iban;
import com.example.transactions.domain.model.transaction.Reference;
import com.example.transactions.domain.model.transaction.Transaction;
import com.example.transactions.domain.model.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SearchTransactionsTest {

  private static final String REFERENCE = "reference";

  private static final double AMOUNT = 10.0;

  private static final double FEE = 1.0;

  private static final String DESCRIPTION = "description";

  private static final long DATE = 1681087396L;

  private static final Iban IBAN = Iban.create("iban");

  private static final String SORT_DIRECTION = "ASC";

  private static final String A_TEST_STRING = "aTest";

  @Mock
  private TransactionRepository mockTransactionRepository;

  @Mock
  private Transformer<List<Transaction>, String> mockTransformer;

  private SearchTransactions<String> searchTransactions;

  @BeforeEach
  void setUp() {
    searchTransactions = new SearchTransactions<>(mockTransactionRepository, mockTransformer);
  }

  @Nested
  class Execute {

    @Nested
    class GivenExistentTransactions {

      @Test
      void shouldReturnThem() {
        final List<Transaction> transactions = transactions();
        when(mockTransactionRepository.findByIban(IBAN, SORT_DIRECTION)).thenReturn(transactions);
        when(mockTransformer.transform(transactions())).thenReturn(A_TEST_STRING);

        String searchedTransactions =
            searchTransactions.execute(SearchTransactionsParams.builder().iban(IBAN).sort(SORT_DIRECTION).build());

        assertThat(searchedTransactions).isEqualTo(A_TEST_STRING);
      }
    }

    private List<Transaction> transactions() {
      return List.of(Transaction.builder()
          .iban(IBAN)
          .reference(Reference.create(REFERENCE))
          .amount(AMOUNT)
          .fee(FEE)
          .description(DESCRIPTION)
          .date(Instant.ofEpochMilli(DATE))
          .build());
    }
  }
}
