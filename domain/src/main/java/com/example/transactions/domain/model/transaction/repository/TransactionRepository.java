package com.example.transactions.domain.model.transaction.repository;

import java.util.List;

import com.example.transactions.domain.model.common.repository.BaseRepository;
import com.example.transactions.domain.model.transaction.Iban;
import com.example.transactions.domain.model.transaction.Transaction;

public interface TransactionRepository extends BaseRepository<Transaction> {

  List<Transaction> findByIban(Iban iban, String sort);

}
