package org.lucas.example.framework.spring.thread;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(locations = {"classpath:thread-applicationContext.xml"})
public class ThreadPoolTaskExecutorDemo {

    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Test
    public void testAsyncExecutor() {

    }

}
