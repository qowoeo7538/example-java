package org.lucas.example.framework.web.spring.controller;

import org.junit.Test;
import org.lucas.example.framework.web.spring.BaseSpringMvcTest;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ValidationControllerTests extends BaseSpringMvcTest {

    @Test
    public void testValidationAsyncOrder() throws Exception {
        MvcResult result = mockMvc.perform(post("/validation/order")).andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("validation ok.")));
    }

    @Test
    public void testValidationOrder() throws Exception {
        MultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("name", "zhangsan");
        valueMap.add("ccNumber", "132456");
        valueMap.add("ccExpiration", "12/12");
        valueMap.add("ccCVV","234");

        mockMvc.perform(
                MockMvcRequestBuilders.get("/validation/order")
                        .params(valueMap))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("validation ok.")));
    }

}
