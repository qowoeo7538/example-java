package org.lucas.example.framework.web.spring;

import org.junit.runner.RunWith;
import org.lucas.example.framework.web.spring.config.ServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * RunWith：测试运行器，构建 Spring 上下文。
 * WebMvcTest: 使测试用例在 spring mvc 上下文执行。
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerConfig.class)
@AutoConfigureMockMvc
public abstract class BaseSpringMvcTest {

    @Autowired
    protected MockMvc mockMvc;

    protected MockHttpSession session;

}
