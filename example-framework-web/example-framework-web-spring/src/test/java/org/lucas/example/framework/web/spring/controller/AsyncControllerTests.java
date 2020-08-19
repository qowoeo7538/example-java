package org.lucas.example.framework.web.spring.controller;

import org.junit.Test;
import org.lucas.example.framework.web.spring.BaseSpringMvcTest;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AsyncControllerTests extends BaseSpringMvcTest {

    @Test
    public void testDeferredResult() throws Exception {
        // 异步 mock 结果
        mockMvc.perform(asyncDispatch(mockMvc.perform(get("/async/deferredResult")).andReturn()))
                // 期望 status 为 200
                .andExpect(status().isOk())
                // 期望内容包含 "deferredResult ok."
                .andExpect(content().string(containsString("deferredResult ok.")));
    }
}
