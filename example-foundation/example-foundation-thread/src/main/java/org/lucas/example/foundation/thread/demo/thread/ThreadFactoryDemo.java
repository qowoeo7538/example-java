package org.lucas.example.foundation.thread.demo.thread;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.thread.demo.executors.support.FactoryTask;
import org.lucas.example.foundation.thread.demo.thread.support.ThreadFactoryImpl;

/**
 * @create: 2017-11-08
 * @description: 自定义线程对象创建
 */
public class ThreadFactoryDemo {

    @Test
    public void demo() {
        // 创建线程工厂
        ThreadFactoryImpl factory = new ThreadFactoryImpl("CustomThreadFactory");
        // Runnable对象
        FactoryTask task = new FactoryTask();
        Thread thread;
        System.out.printf("Starting the Threads\n\n");
        for (int i = 1; i <= 10; i++) {
            thread = factory.newThread(task);
            thread.start();
        }
        System.out.printf("All Threads are created now\n\n");
        System.out.printf("Give me CustomThreadFactory stats:\n\n" + factory.getStats());
    }

}
