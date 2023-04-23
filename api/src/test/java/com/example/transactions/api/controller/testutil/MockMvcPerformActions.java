package com.example.transactions.api.controller.testutil;

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

  public MockMvcPerformActions andExpectStatusIsOk() throws Exception {
    this.resultActions = resultActions.andExpect(status().isOk());
    return this;
  }

  public MockMvcPerformActions andExpectStatusIsBadRequest() throws Exception {
    this.resultActions = resultActions.andExpect(status().isBadRequest());
    return this;
  }

  public <T> T getResponseBody(Class<T> clazz) throws Exception {
    String responseString = resultActions.andReturn()
        .getResponse()
        .getContentAsString();
    return MAPPER.readValue(responseString, clazz);
  }

  private static ObjectMapper createMapper() {
    JavaTimeModule module = new JavaTimeModule();
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(module);
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    return objectMapper;
  }
}

