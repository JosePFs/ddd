package com.example.transactions.config;

import static com.example.transactions.testutil.MockMvcPerformActions.MAPPER;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.example.api.model.TransactionDTO;
import com.example.transactions.testutil.MockMvcPerformActions;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class MvcConfiguration {

  @Autowired
  protected MockMvc mockMvc;

  protected MockMvcPerformActions mockMvcPerformActions;

  protected void perform(MockHttpServletRequestBuilder request) throws Exception {
    ResultActions resultActions = mockMvc.perform(request.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
    mockMvcPerformActions = new MockMvcPerformActions(resultActions);
  }

  public void expectStatusIsOk() throws Exception {
    mockMvcPerformActions.andExpectStatusIsOk();
  }

  public void expectStatusIsBadRequest() throws Exception {
    mockMvcPerformActions.andExpectStatusIsBadRequest();
  }

  public void expectedTransactionsResponse(List<TransactionDTO> transactionDTOS) throws Exception {
    assertThat(mockMvcPerformActions.getResponseBody(TransactionDTO[].class)).isEqualTo(transactionDTOS.toArray());
  }

  protected static String asJsonString(final Object obj) {
    try {
      return MAPPER.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
