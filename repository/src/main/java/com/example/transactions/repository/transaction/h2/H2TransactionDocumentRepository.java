package com.example.transactions.repository.transaction.h2;

import java.util.List;

import com.example.transactions.domain.model.transaction.Iban;
import com.example.transactions.domain.model.transaction.Transaction;
import com.example.transactions.domain.model.transaction.repository.TransactionRepository;
import com.example.transactions.repository.transaction.h2.document.TransactionDocumentDAO;
import com.example.transactions.repository.transaction.h2.document.TransactionDocumentMapper;
import com.example.transactions.repository.transaction.h2.exception.DuplicatedReferenceException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

@Repository
public class H2TransactionDocumentRepository implements TransactionRepository {

  private static final String AMOUNT_FIELD = "amount";

  private final TransactionDocumentDAO transactionDocumentDAO;

  private final TransactionDocumentMapper transactionDocumentMapper;

  public H2TransactionDocumentRepository(TransactionDocumentDAO transactionDocumentDAO, TransactionDocumentMapper transactionDocumentMapper) {
    this.transactionDocumentDAO = transactionDocumentDAO;
    this.transactionDocumentMapper = transactionDocumentMapper;
  }

  @Override
  public Transaction create(Transaction entity) {
    if (transactionDocumentDAO.existsById(entity.getReference().getValue())) {
      throw new DuplicatedReferenceException(String.format("Reference '%s' already exists", entity.getReference().getValue()));
    }

    return transactionDocumentMapper.toTransactions(transactionDocumentDAO.save(transactionDocumentMapper.toTransactionDocument(entity)));
  }

  @Override
  public List<Transaction> findByIban(Iban iban, String sort) {
    return transactionDocumentMapper
        .toTransactions(transactionDocumentDAO.findByIban(iban.getValue(), Sort.by(Direction.valueOf(sort), AMOUNT_FIELD)));
  }
}
