package org.lucas.example.foundation.basic.demo.struct.concurrent.queue.support.blockingqueue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @create: 2017-11-10
 * @description: 模拟消费端
 */
public class Consumer implements Runnable {

    private static final int DEFAULT_RANGE_FOR_SLEEP = 1000;

    private BlockingQueue<String> queue;

    public Consumer(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("启动消费者线程！");
        Random random = new Random();
        boolean isRunning = true;
        while (isRunning) {
            try {
                System.out.println("正从队列获取数据...");
                String data = queue.poll(2, TimeUnit.SECONDS);
                if (null != data) {
                    System.out.println("拿到数据：" + data);
                    System.out.println("正在消费数据：" + data);
                    // 高并发CAS锁竞争
                    Thread.sleep(random.nextInt(DEFAULT_RANGE_FOR_SLEEP));
                } else {
                    // 超过2s还没数据，认为所有生产线程都已经退出，自动退出消费线程。
                    isRunning = false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            } finally {
                System.out.println("退出消费者线程！");
            }
        }
    }
}
