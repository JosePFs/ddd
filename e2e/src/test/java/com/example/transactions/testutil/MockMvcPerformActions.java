package com.example.transactions.testutil;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.test.web.servlet.ResultActions;

public class MockMvcPerformActions {

  public static final ObjectMapper MAPPER = createMapper();

  private ResultActions resultActions;

  public MockMvcPerformActions(ResultActions resultActions) {
    this.resultActions = resultActions;
  }

  public void andExpectStatusIsOk() throws Exception {
    this.resultActions = resultActions.andExpect(status().isOk());
  }

  public void andExpectStatusIsBadRequest() throws Exception {
    this.resultActions = resultActions.andExpect(status().isBadRequest());
  }

  public <T> T getResponseBody(Class<T> classOfT) throws Exception {
    String responseString = resultActions.andReturn()
        .getResponse()
        .getContentAsString();
    return MAPPER.readValue(responseString, classOfT);
  }

  private static ObjectMapper createMapper() {
    JavaTimeModule module = new JavaTimeModule();
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(module);
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    return objectMapper;
  }
}

