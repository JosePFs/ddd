package com.example.transactions.application.usecase.searchtransactions;

import java.util.List;

import com.example.transactions.domain.model.common.transformer.Transformer;
import com.example.transactions.domain.model.transaction.Transaction;
import com.example.transactions.domain.model.transaction.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class SearchTransactions<T> {

  private final TransactionRepository transactionRepository;

  private final Transformer<List<Transaction>, T> transactionTransformer;

  public SearchTransactions(TransactionRepository transactionRepository, Transformer<List<Transaction>, T> transactionTransformer) {
    this.transactionRepository = transactionRepository;
    this.transactionTransformer = transactionTransformer;
  }

  public T execute(SearchTransactionsParams params) {
    List<Transaction> transactions = transactionRepository.findByIban(params.getIban(), params.getSort());
    return transactionTransformer.transform(transactions);
  }
}
