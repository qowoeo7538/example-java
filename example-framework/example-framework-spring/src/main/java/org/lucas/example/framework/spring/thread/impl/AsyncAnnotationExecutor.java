package org.lucas.example.framework.spring.thread.impl;

import org.springframework.scheduling.annotation.Async;

public class AsyncAnnotationExecutor {

    @Async
    public void printMessages() {
        for (int i = 0; i < 6; i++) {
            try {
                Thread.sleep(500);
                System.out.println(Thread.currentThread().getName() + " message：" + i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
