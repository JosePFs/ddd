package com.example.transactions.accountclient.account.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDTODAO extends JpaRepository<AccountDTO, String> {

  Optional<AccountDTO> findByIban(String iban);

}
