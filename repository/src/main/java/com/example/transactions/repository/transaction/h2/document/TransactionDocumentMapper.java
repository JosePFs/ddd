package com.example.transactions.repository.transaction.h2.document;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.transactions.domain.model.transaction.Iban;
import com.example.transactions.domain.model.transaction.Reference;
import com.example.transactions.domain.model.transaction.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionDocumentMapper {

  public List<Transaction> toTransactions(List<TransactionDocument> transactionDocuments) {
    return transactionDocuments.stream().map(this::toTransactions).collect(Collectors.toList());
  }

  public Transaction toTransactions(TransactionDocument transactionDocument) {
    return Transaction.builder()
        .amount(transactionDocument.getAmount())
        .date(Optional.ofNullable(transactionDocument.getDate()).map(Instant::ofEpochMilli).orElse(null))
        .description(transactionDocument.getDescription())
        .fee(transactionDocument.getFee())
        .reference(Reference.create(transactionDocument.getReference()))
        .iban(Iban.create(transactionDocument.getIban()))
        .build();
  }

  public TransactionDocument toTransactionDocument(Transaction transaction) {
    TransactionDocument transactionDocument = new TransactionDocument();
    transactionDocument.setAmount(transaction.getAmount());
    transactionDocument.setDate(transaction.getDate().toEpochMilli());
    transactionDocument.setFee(transaction.getFee());
    transactionDocument.setDescription(transaction.getDescription().orElse(null));
    transactionDocument.setReference(transaction.getReference().getValue());
    transactionDocument.setIban(transaction.getIban().getValue());
    return transactionDocument;
  }
}
