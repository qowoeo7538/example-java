package org.lucas.example.framework.spring.thread;

import org.junit.jupiter.api.Test;
import org.lucas.example.framework.spring.thread.impl.AsyncExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(locations = {"classpath:applicationContext-thread.xml"})
public class ThreadPoolTaskExecutorDemo {

    @Autowired
    private GenericApplicationContext ctx;

    @Test
    public void demoAsyncExecutor() {
        AsyncExecutor asyncExecutor = ctx.getBean(AsyncExecutor.class);
        System.out.println(Thread.currentThread().getName() + " begin ");
        asyncExecutor.printMessages();
        System.out.println(Thread.currentThread().getName() + " end ");
    }

}
