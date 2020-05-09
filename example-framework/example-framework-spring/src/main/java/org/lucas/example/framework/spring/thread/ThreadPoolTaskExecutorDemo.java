package org.lucas.example.framework.spring.thread;

import org.junit.jupiter.api.Test;
import org.lucas.example.framework.spring.thread.impl.AsyncAnnotationExecutor;
import org.lucas.example.framework.spring.thread.impl.AsyncExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.LockSupport;

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
        CompletableFuture<String> resultFuture = asyncExecutor.doSomething();
        Thread mainThread = Thread.currentThread();
        resultFuture.whenCompleteAsync((t, u) -> {
            if (null == u) {
                System.out.println(Thread.currentThread().getName() + " " + t);
            } else {
                System.out.println("error:" + u.getLocalizedMessage());
            }
            LockSupport.unpark(mainThread);
        });
        LockSupport.park(mainThread);
        System.out.println(Thread.currentThread().getName() + " end ");
    }

}
