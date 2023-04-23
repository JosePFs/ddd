Feature: Create a transaction

  Scenario: client create a new transaction
    Given A new transaction of existent account
    When the client calls transactions
    Then system returns success

  Scenario: client create a transaction of non existent account
    Given A new transaction of non existent account
    When the client calls transactions
    Then system returns error

  Scenario: client create a transaction that reduce balance account under zero
    Given A new transaction of negative balance account
    When the client calls transactions
    Then system returns error
