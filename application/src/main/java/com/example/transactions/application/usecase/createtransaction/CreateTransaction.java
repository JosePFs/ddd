package com.example.transactions.application.usecase.createtransaction;

import com.example.transactions.domain.model.transaction.Account;
import com.example.transactions.domain.model.transaction.Iban;
import com.example.transactions.domain.model.transaction.Transaction;
import com.example.transactions.domain.model.transaction.repository.TransactionRepository;
import com.example.transactions.domain.service.AccountService;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CreateTransaction {

  private final AccountService accountService;

  private final TransactionRepository transactionRepository;

  public CreateTransaction(AccountService accountService, TransactionRepository transactionRepository) {
    this.accountService = accountService;
    this.transactionRepository = transactionRepository;
  }

  @Transactional
  public void execute(CreateTransactionParams createTransactionParams) {
    Account account = accountService.getAccountByIban(Iban.create(createTransactionParams.getIban()));

    Transaction transaction = createTransactionParams.toTransaction();
    Account updatedAccount = transaction.applyToAccount(account);

    transactionRepository.create(transaction);
    accountService.update(updatedAccount);
  }
}
