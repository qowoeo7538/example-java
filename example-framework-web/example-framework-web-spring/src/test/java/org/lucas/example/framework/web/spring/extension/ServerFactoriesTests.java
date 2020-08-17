package org.lucas.example.framework.web.spring.extension;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lucas.example.framework.web.spring.config.ServerConfig;
import org.lucas.example.framework.web.spring.extension.impl.AServer;
import org.lucas.example.framework.web.spring.extension.impl.BServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * RunWith：测试运行器，构建 Spring 上下文。
 * SpringBootTest：添加 spring boot 功能。
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerConfig.class)
public class ServerFactoriesTests {

    @Autowired
    private ServerFactories serverFactories;

    @Test
    public void getAServer() {
        AServer server = serverFactories.getServer(AServer.UUID);
        System.out.println(server.callA("test"));
    }

    @Test
    public void getBServer() {
        BServer server = serverFactories.getServer(BServer.UUID);
        System.out.println(server.callB("test"));
    }

}
