package com.example.transactions.steps;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.List;

import com.example.api.model.TransactionDTO;
import com.example.transactions.config.MvcConfiguration;
import com.example.transactions.repository.transaction.h2.document.TransactionDocument;
import com.example.transactions.repository.transaction.h2.document.TransactionDocumentDAO;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

@CucumberContextConfiguration
public class TransactionStepsDefinitions extends MvcConfiguration {

  private static final String TRANSACTIONS_PATH = "/transactions";

  private static final String TRANSACTIONS_SEARCH_PATH = "/transactions/iban/{account_iban}";

  private static final String TEN_IBAN = "tenIban";

  private static final String ZERO_IBAN = "zeroIban";

  private static final String REFERENCE_ONE = "referenceOne";

  private static final String REFERENCE_TWO = "referenceTwo";

  private static final TransactionDTO TRANSACTION_ONE;

  private static final TransactionDTO TRANSACTION_TWO;

  static {
    TRANSACTION_ONE = new TransactionDTO(TEN_IBAN, 10.0);
    TRANSACTION_ONE.setReference(REFERENCE_ONE);

    TRANSACTION_TWO = new TransactionDTO(TEN_IBAN, 100.0);
    TRANSACTION_TWO.setReference(REFERENCE_TWO);
  }

  @Autowired
  private TransactionDocumentDAO transactionDocumentDAO;

  private TransactionDTO transaction;

  @After
  public void tearDown() {
    transactionDocumentDAO.deleteAll();
  }

  @Given("A new transaction of existent account")
  public void aNewTransactionThatIsNotStoredInOurSystem() {
    transaction = new TransactionDTO(TEN_IBAN, 100.0).description("description").fee(1.0);
  }

  @When("the client calls transactions")
  public void theClientCallsTransactions() throws Exception {
    perform(post(TRANSACTIONS_PATH).content(asJsonString(transaction)));
  }

  @Then("system returns success")
  public void systemReturnsSuccess() throws Exception {
    expectStatusIsOk();
  }

  @Given("A new transaction of non existent account")
  public void aNewTransactionOfNonExistentAccount() {
    transaction = new TransactionDTO("nonExistentAccount", 100.0).description("description").fee(1.0);
  }

  @Given("A new transaction of negative balance account")
  public void aNewTransactionOfNegativeBalanceAccount() {
    transaction = new TransactionDTO(ZERO_IBAN, -10.0).description("description").fee(1.0);
  }

  @Then("system returns error")
  public void systemReturnsError() throws Exception {
    expectStatusIsBadRequest();
  }

  @Given("existent account transactions")
  public void existentAccountTransactions() {
    transactionDocumentDAO.saveAll(transactionDocuments());
  }

  @When("the client calls transactions search")
  public void theClientCallsTransactionsSearch() throws Exception {
    perform(get(TRANSACTIONS_SEARCH_PATH, TEN_IBAN));
  }

  @Then("system returns transactions")
  public void systemReturnsTransactions() throws Exception {
    expectedTransactionsResponse(List.of(TRANSACTION_ONE, TRANSACTION_TWO));
  }

  @When("the client calls transactions search sorting desc")
  public void theClientCallsTransactionsSearchSortingDesc() throws Exception {
    perform(get(TRANSACTIONS_SEARCH_PATH, TEN_IBAN).param("sort", "desc"));
  }

  @Then("system returns transactions sorted")
  public void systemReturnsTransactionsSorted() throws Exception {
    expectedTransactionsResponse(List.of(TRANSACTION_TWO, TRANSACTION_ONE));
  }

  @When("the client calls non existent transactions search")
  public void theClientCallsNonExistentTransactionsSearch() throws Exception {
    perform(get(TRANSACTIONS_SEARCH_PATH, "unknown").param("sort", "desc"));
  }

  @Then("system returns empty")
  public void systemReturnsEmpty() throws Exception {
    expectedTransactionsResponse(List.of());
  }

  private static List<TransactionDocument> transactionDocuments() {
    TransactionDocument transactionDocumentOne = new TransactionDocument();
    transactionDocumentOne.setIban(TEN_IBAN);
    transactionDocumentOne.setAmount(10.0);
    transactionDocumentOne.setReference(REFERENCE_ONE);

    TransactionDocument transactionDocumentTwo = new TransactionDocument();
    transactionDocumentTwo.setIban(TEN_IBAN);
    transactionDocumentTwo.setAmount(100.0);
    transactionDocumentTwo.setReference(REFERENCE_TWO);

    return List.of(transactionDocumentOne, transactionDocumentTwo);
  }
}
