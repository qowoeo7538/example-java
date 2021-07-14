package org.lucas.example.foundation.thread.demo.executors;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @create: 2017-11-17
 * @description: 延时执行器
 */
public class ScheduledThreadDemo {

    @Test
    public void demo() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
        for (int i = 0; i < 10; i++) {
            final int index = i;
            Runnable task = () -> System.out.println(Thread.currentThread().getName() + ">> delay " + index + " seconds run....");
            ScheduledFuture<?> schedule = scheduledThreadPool.schedule(task, i, TimeUnit.SECONDS);
        }
        scheduledThreadPool.shutdown();
    }

    /**
     * 固定频率
     */
    @Test
    public void demoScheduleAtFixedRate() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(12);
        Runnable task = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + ">> sleep..." + System.currentTimeMillis());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ">> run....." + System.currentTimeMillis());
        };
        ScheduledFuture<?> scheduleAtFixedRate = scheduledThreadPool.scheduleAtFixedRate(task, 0, 2, TimeUnit.SECONDS);
        // 任务取消,将不会执行,只对未执行的任务有效
        scheduleAtFixedRate.cancel(false);
        scheduledThreadPool.shutdown();
        try {
            scheduledThreadPool.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Test
    public void demoScheduleWithFixedDelay() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(10);
        Runnable task = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + ">> sleep..." + System.currentTimeMillis());
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ">> run....." + System.currentTimeMillis());
        };
        ScheduledFuture<?> scheduleWithFixedDelay = scheduledThreadPool.scheduleWithFixedDelay(task, 0, 2, TimeUnit.SECONDS);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        scheduleWithFixedDelay.cancel(true);
        scheduledThreadPool.shutdown();
    }

}
