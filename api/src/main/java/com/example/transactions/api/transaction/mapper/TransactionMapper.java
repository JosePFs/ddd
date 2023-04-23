package com.example.transactions.api.transaction.mapper;

import java.util.Optional;

import com.example.api.model.TransactionDTO;
import com.example.transactions.application.usecase.createtransaction.CreateTransactionParams;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

  public CreateTransactionParams mapToParams(TransactionDTO transaction) {
    return CreateTransactionParams.builder()
        .iban(transaction.getAccountIban())
        .amount(transaction.getAmount())
        .reference(transaction.getReference())
        .fee(transaction.getFee())
        .date(Optional.ofNullable(transaction.getDate())
            .map(offsetDateTime -> offsetDateTime.toInstant().toEpochMilli())
            .orElse(null))
        .description(transaction.getDescription())
        .build();
  }
}
