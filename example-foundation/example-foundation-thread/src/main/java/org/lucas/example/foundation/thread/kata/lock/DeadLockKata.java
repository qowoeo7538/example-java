package org.lucas.example.foundation.thread.kata.lock;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.core.task.ExampleThreadExecutor;

public class DeadLockKata {

    private static String A = "A";

    private static String B = "B";

    @Test
    public void deadLock() {
        System.out.println(Thread.currentThread().getId());
        ExampleThreadExecutor.execute(() -> {
            synchronized (A) {
                try {
                    Thread.sleep(2000);
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (B) {
                    System.out.println("1");
                }
            }
        });
        ExampleThreadExecutor.execute(() -> {
            synchronized (B) {
                synchronized (A) {
                    System.out.println("2");
                }
            }
        });
        ExampleThreadExecutor.destroy();
    }
}
