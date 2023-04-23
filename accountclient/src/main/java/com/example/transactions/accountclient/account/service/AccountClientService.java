package com.example.transactions.accountclient.account.service;

import com.example.transactions.accountclient.account.model.AccountDTO;
import com.example.transactions.accountclient.account.model.AccountDTODAO;
import com.example.transactions.domain.exception.NotFoundException;
import com.example.transactions.domain.model.transaction.Account;
import com.example.transactions.domain.model.transaction.Iban;
import com.example.transactions.domain.service.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountClientService implements AccountService {

  private final AccountDTODAO accountDTODAO;

  public AccountClientService(AccountDTODAO accountDTODAO) {
    this.accountDTODAO = accountDTODAO;
  }

  @Override
  public Account getAccountByIban(Iban iban) {
    return accountDTODAO.findByIban(iban.getValue())
        .map(AccountClientService::mapToAccount)
        .orElseThrow(() -> new NotFoundException(String.format("Account with iban '%s' not found.", iban.getValue())));
  }

  @Override
  public Account update(Account account) {
    return mapToAccount(accountDTODAO.save(mapToAccountDTO(account)));
  }

  private static Account mapToAccount(AccountDTO accountDTO) {
    return Account.builder()
        .iban(Iban.create(accountDTO.getIban()))
        .balance(accountDTO.getBalance())
        .build();
  }

  private static AccountDTO mapToAccountDTO(Account account) {
    AccountDTO accountDTO = new AccountDTO();
    accountDTO.setIban(account.getIban().getValue());
    accountDTO.setBalance(account.getBalance());
    return accountDTO;
  }
}
