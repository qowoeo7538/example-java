package org.lucas.example.framework.web.spring.controller;

import org.junit.Before;
import org.junit.Test;
import org.lucas.example.framework.web.spring.BaseSpringMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SessionControllerTests extends BaseSpringMvcTest {

    @Before
    public void setUp() throws Exception {
        session = new MockHttpSession();

        MockHttpServletRequestBuilder sessionSetRequest = MockMvcRequestBuilders
                .post("/session")
                .session(session);

        mockMvc.perform(sessionSetRequest)
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("index")));

        MockHttpServletRequestBuilder sessionGetRequest = MockMvcRequestBuilders
                .get("/session")
                .session(session);

        mockMvc.perform(sessionGetRequest).andExpect(status().isOk());
    }

    @Test
    public void testGetOrder() throws Exception {
        MockHttpServletRequestBuilder sessionGetRequest = MockMvcRequestBuilders
                .get("/session/order")
                .param("order", "1")
                .session(session);

        mockMvc.perform(sessionGetRequest).andExpect(status().isOk());
    }

    @Test
    public void testGetModel() throws Exception {
        session = new MockHttpSession();
        MockHttpServletRequestBuilder sessionGetRequest = MockMvcRequestBuilders
                .get("/session/model")
                .param("param", "123")
                .session(session);
        mockMvc.perform(sessionGetRequest).andExpect(status().isOk())
                .andExpect(content().string(containsString("123")));
    }

}
