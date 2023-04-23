Feature: Search transactions by iban

  Scenario: client search by iban not sorting
    Given existent account transactions
    When the client calls transactions search
    Then system returns transactions

  Scenario: client search by iban and sort desc
    Given existent account transactions
    When the client calls transactions search sorting desc
    Then system returns transactions sorted

  Scenario: client search by non existent iban
    Given existent account transactions
    When the client calls non existent transactions search
    Then system returns empty
