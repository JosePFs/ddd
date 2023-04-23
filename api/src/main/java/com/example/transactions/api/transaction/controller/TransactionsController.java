package com.example.transactions.api.transaction.controller;

import static org.springframework.http.ResponseEntity.ok;

import java.util.List;

import com.example.api.TransactionsApi;
import com.example.api.model.TransactionDTO;
import com.example.transactions.api.transaction.mapper.TransactionMapper;
import com.example.transactions.application.usecase.createtransaction.CreateTransaction;
import com.example.transactions.application.usecase.searchtransactions.SearchTransactions;
import com.example.transactions.application.usecase.searchtransactions.SearchTransactionsParams;
import com.example.transactions.domain.model.transaction.Iban;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionsController implements TransactionsApi {

  private final TransactionMapper transactionMapper;

  private final CreateTransaction createTransaction;

  private final SearchTransactions<List<TransactionDTO>> searchTransactions;

  public TransactionsController(TransactionMapper transactionMapper, CreateTransaction createTransaction,
      SearchTransactions<List<TransactionDTO>> searchTransactions) {
    this.transactionMapper = transactionMapper;
    this.createTransaction = createTransaction;
    this.searchTransactions = searchTransactions;
  }

  @Override
  public ResponseEntity<Void> createTransaction(TransactionDTO transactionDTO) {
    createTransaction.execute(transactionMapper.mapToParams(transactionDTO));
    return ok().build();
  }

  @Override
  public ResponseEntity<List<TransactionDTO>> searchTransactions(String accountIban, String sort) {
    SearchTransactionsParams params = SearchTransactionsParams.builder()
        .iban(Iban.create(accountIban))
        .sort(sort)
        .build();
    return ok(searchTransactions.execute(params));
  }
}
