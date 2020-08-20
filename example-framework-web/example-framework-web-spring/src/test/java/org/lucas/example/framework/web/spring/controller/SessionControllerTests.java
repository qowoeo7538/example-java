package org.lucas.example.framework.web.spring.controller;

import org.junit.Test;
import org.lucas.example.framework.web.spring.BaseSpringMvcTest;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SessionControllerTests extends BaseSpringMvcTest {

    @Test
    public void testGetUser() throws Exception {
        MockHttpServletRequestBuilder sessionSetRequest = MockMvcRequestBuilders.post("/session")
                .session(session);

        mockMvc.perform(sessionSetRequest)
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("index")));

        MockHttpServletRequestBuilder sessionGetRequest = MockMvcRequestBuilders.get("/session")
                .session(session);

        mockMvc.perform(sessionGetRequest).andExpect(status().isOk());
    }

    @Test
    public void testGetOrder() throws Exception {
        MockHttpServletRequestBuilder sessionSetRequest = MockMvcRequestBuilders.post("/session")
                .session(session);

        mockMvc.perform(sessionSetRequest)
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("index")));

        MockHttpServletRequestBuilder sessionGetRequest = MockMvcRequestBuilders.get("/order")
                .session(session);

        mockMvc.perform(sessionGetRequest).andExpect(status().isOk());
    }

}
