package com.example.transactions.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

import com.example.api.model.TransactionDTO;
import com.example.transactions.api.controller.config.Application;
import com.example.transactions.api.controller.testutil.MockMvcTest;
import com.example.transactions.api.transaction.controller.TransactionsController;
import com.example.transactions.api.transaction.exception.ApiError;
import com.example.transactions.api.transaction.exception.handler.ApiExceptionHandler;
import com.example.transactions.api.transaction.mapper.TransactionMapper;
import com.example.transactions.api.transaction.transformer.TransactionsTransformer;
import com.example.transactions.application.usecase.createtransaction.CreateTransaction;
import com.example.transactions.application.usecase.createtransaction.CreateTransactionParams;
import com.example.transactions.application.usecase.searchtransactions.SearchTransactions;
import com.example.transactions.domain.exception.TransactionsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes= {TransactionsController.class, TransactionMapper.class, CreateTransaction.class,
    Application.class, ApiExceptionHandler.class})
public class TransactionsControllerTest extends MockMvcTest {

  private static final String TRANSACTIONS_PATH = "/transactions";

  private static final String TRANSACTIONS_SEARCH_PATH = "/transactions/iban/{account_iban}";

  private static final String REFERENCE = "reference";

  private static final double AMOUNT = 10.0;

  private static final double FEE = 1.0;

  private static final String DESCRIPTION = "description";

  private static final long DATE = 1681087396L;

  private static final String IBAN = "iban";

  @MockBean
  private CreateTransaction mockCreateTransaction;

  @MockBean
  private SearchTransactions<List<TransactionDTO>> mockSearchTransactions;

  @MockBean
  private TransactionsTransformer mockTransactionsTransformer;

  @BeforeEach
  void clearMocks() {
    Mockito.reset(mockCreateTransaction, mockSearchTransactions, mockTransactionsTransformer);
  }

  @Nested
  class CreateTransactionEndpoint {

    @Nested
    class GivenValidParams {

      @Test
      void shouldCallUseCaseAndReturnSuccess() throws Exception {
        perform(post(TRANSACTIONS_PATH).content(asJsonString(transactionDTO()))).andExpectStatusIsOk();

        verify(mockCreateTransaction).execute(createTransactionParams());
      }
    }

    @Nested
    class GivenNonValidParams {

      @Test
      void shouldCallUseCaseAndReturnError() throws Exception {
        doThrow(new TransactionsException("error")).when(mockCreateTransaction).execute(createTransactionParams());

        ApiError apiError = perform(post(TRANSACTIONS_PATH).content(asJsonString(transactionDTO())))
            .andExpectStatusIsBadRequest()
            .getResponseBody(ApiError.class);

        assertThat(apiError.getCode()).isEqualTo(400);
      }
    }

    private TransactionDTO transactionDTO() {
      return new TransactionDTO(IBAN, AMOUNT)
          .reference(REFERENCE)
          .fee(FEE)
          .description(DESCRIPTION)
          .date(Instant.ofEpochMilli(DATE).atOffset(ZoneOffset.UTC));
    }

    private CreateTransactionParams createTransactionParams() {
      return CreateTransactionParams.builder()
          .iban(IBAN)
          .reference(REFERENCE)
          .amount(AMOUNT)
          .fee(FEE)
          .description(DESCRIPTION)
          .date(DATE)
          .build();
    }
  }

  @Nested
  class SearchTransactionsEndpoint {

    @BeforeEach
    void setUp() {
      when(mockSearchTransactions.execute(any())).thenReturn(List.of());
    }

    @Nested
    class GivenValidDefaultParams {

      @Test
      void shouldCallUseCaseAndReturnSuccess() throws Exception {
        perform(get(TRANSACTIONS_SEARCH_PATH, IBAN))
            .andExpectStatusIsOk();

        verify(mockSearchTransactions).execute(argThat(searchTransactionsParams ->
            searchTransactionsParams.getIban().getValue().equals(IBAN) && searchTransactionsParams.getSort().equals("ASC")));
      }
    }

    @Nested
    class GivenValidDescParams {

      @Test
      void shouldCallUseCaseAndReturnSuccess() throws Exception {
        perform(get(TRANSACTIONS_SEARCH_PATH, IBAN).param("sort", "desc"))
            .andExpectStatusIsOk();

        verify(mockSearchTransactions).execute(argThat(searchTransactionsParams ->
            searchTransactionsParams.getIban().getValue().equals(IBAN) && searchTransactionsParams.getSort().equals("DESC")));
      }
    }
  }
}
