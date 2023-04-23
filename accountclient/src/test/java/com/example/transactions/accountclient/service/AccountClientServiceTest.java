package com.example.transactions.accountclient.service;

import com.example.transactions.accountclient.account.model.AccountDTODAO;
import com.example.transactions.accountclient.account.service.AccountClientService;
import com.example.transactions.domain.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountClientServiceTest {

  @Mock
  private AccountDTODAO mockAccountDTODAO;

  private AccountService accountService;

  @BeforeEach
  void setUp() {
    accountService = new AccountClientService(mockAccountDTODAO);
  }

  @Nested
  class GetAccountByIban {

    @Nested
    class GivenAnExistentAccount {

      @Test
      void shouldReturnIt() {}
    }

    @Nested
    class GivenANonExistentAccount {

      @Test
      void shouldThrowAnError() {}
    }
  }

  @Nested
  class Update {

    @Nested
    class GivenAnExistentAccount {

      @Test
      void shouldReturnIt() {}
    }
  }
}
