package com.example.transactions.domain.service;

import com.example.transactions.domain.model.transaction.Account;
import com.example.transactions.domain.model.transaction.Iban;

public interface AccountService {

  Account getAccountByIban(Iban iban);

  Account update(Account account);

}
