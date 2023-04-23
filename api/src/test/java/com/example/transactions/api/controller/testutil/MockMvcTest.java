package com.example.transactions.api.controller.testutil;

import static com.example.transactions.api.controller.testutil.MockMvcPerformActions.MAPPER;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@WebMvcTest
public abstract class MockMvcTest {

  @Autowired
  protected MockMvc mockMvc;

  protected MockMvcPerformActions mockMvcPerformActions;

  protected MockMvcPerformActions perform(MockHttpServletRequestBuilder request) throws Exception {
    ResultActions resultActions = mockMvc.perform(request.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
    return new MockMvcPerformActions(resultActions);
  }

  protected static String asJsonString(final Object obj) {
    try {
      return MAPPER.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
