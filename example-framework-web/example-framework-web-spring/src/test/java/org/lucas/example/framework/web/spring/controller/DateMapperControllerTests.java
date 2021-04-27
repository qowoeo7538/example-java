package org.lucas.example.framework.web.spring.controller;

import org.junit.jupiter.api.Test;
import org.lucas.example.framework.web.spring.BaseSpringMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DateMapperControllerTests extends BaseSpringMvcTest {

    @Test
    private void testDateMapper() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/dateMapper/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"birthday\":\"2021-05-05\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

}
