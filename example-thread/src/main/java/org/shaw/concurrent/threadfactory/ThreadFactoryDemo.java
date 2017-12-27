package org.shaw.concurrent.threadfactory;

import org.shaw.concurrent.threadfactory.impl.CustomThreadFactory;
import org.shaw.concurrent.threadfactory.impl.Task;

/**
 * @create: 2017-11-08
 * @description: 自定义线程对象创建
 */
public class ThreadFactoryDemo {
    public static void main(String[] args) {
        // 创建线程工厂
        CustomThreadFactory factory = new CustomThreadFactory("CustomThreadFactory");
        // Runnable对象
        Task task = new Task();
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
