package org.lucas.example.framework.spring.demo.thread;

import org.junit.jupiter.api.Test;
import org.lucas.example.framework.spring.demo.thread.support.AsyncAnnotationExecutor;
import org.lucas.example.framework.spring.demo.thread.support.AsyncExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(locations = {"classpath:thread/applicationContext-thread.xml"})
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

    @Test
    public void demoAsyncAnnotationExecutor() throws InterruptedException {
        AsyncAnnotationExecutor asyncExecutor = ctx.getBean(AsyncAnnotationExecutor.class);
        System.out.println(Thread.currentThread().getName() + " begin ");
        // 使用 spring 线程池执行，默认使用 SimpleAsyncTaskExecutor 执行。
        asyncExecutor.printMessages();
        // 等待线程完成
        Thread.sleep(3500);
        System.out.println(Thread.currentThread().getName() + " end ");
    }

    @Test
    public void demoAsyncFutureExecutor() throws InterruptedException {
        AsyncAnnotationExecutor asyncExecutor = ctx.getBean(AsyncAnnotationExecutor.class);
        System.out.println(Thread.currentThread().getName() + " begin ");
        // 使用 spring 线程池执行，默认使用 SimpleAsyncTaskExecutor 执行。
        asyncExecutor.callException();
        Thread.sleep(500);
        System.out.println(Thread.currentThread().getName() + " end ");
    }

}
