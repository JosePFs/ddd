package com.example.transactions.repository.transaction.h2.document;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDocumentDAO extends JpaRepository<TransactionDocument, String> {

  List<TransactionDocument> findByIban(String iban, Sort sort);

}
