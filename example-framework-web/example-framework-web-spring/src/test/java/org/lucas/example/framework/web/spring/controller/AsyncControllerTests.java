package org.lucas.example.framework.web.spring.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lucas.example.framework.web.spring.config.ServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * RunWith：测试运行器，构建 Spring 上下文。
 * WebMvcTest: 使测试用例在 spring mvc 上下文执行。
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerConfig.class)
@AutoConfigureMockMvc
public class AsyncControllerTests {

    @Autowired
    private MockMvc mockMvc;

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
