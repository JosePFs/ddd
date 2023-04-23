package com.example.transactions.api.transaction.transformer;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.api.model.TransactionDTO;
import com.example.transactions.domain.model.common.transformer.Transformer;
import com.example.transactions.domain.model.transaction.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionsTransformer extends Transformer<List<Transaction>, List<TransactionDTO>> {

  @Override
  public List<TransactionDTO> transform(List<Transaction> transactions) {
    return transactions.stream().map(TransactionsTransformer::transform).collect(Collectors.toList());
  }

  private static TransactionDTO transform(Transaction transaction) {
    TransactionDTO transactionDTO = new TransactionDTO(transaction.getIban().getValue(), transaction.getAmount());
    transactionDTO.setReference(transaction.getReference().getValue());
    transactionDTO.setFee(transactionDTO.getFee());
    transactionDTO.setDate(Optional.ofNullable(transaction.getDate())
        .map(instant -> instant.atOffset(ZoneOffset.UTC)).orElse(null));
    transactionDTO.setDescription(transaction.getDescription().orElse(null));
    return transactionDTO;
  }
}
