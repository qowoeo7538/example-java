package org.shaw.demo.struct.impl;

import org.shaw.util.DataProducerHelper;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @create: 2017-11-10
 * @description: 模拟生产者
 */
public class Producer implements Runnable {

    /**
     * 控制线程是否停止
     */
    private volatile boolean isRunning = true;

    private static AtomicInteger count = new AtomicInteger();

    private BlockingQueue<String> queue;

    public Producer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        String data;
        System.out.println("启动生产者线程！");
        while (isRunning) {
            System.out.println("正在生产数据...");
            try {
                // 随机睡眠时间
                Thread.sleep(DataProducerHelper.nextInt(1000));
                data = "data:" + count.incrementAndGet();
                System.out.println("将数据：" + data + "放入队列...");

                /**
                 * 如果在指定的时间内，还不能往队列中加入BlockingQueue，则返回失败。
                 */
                if (!queue.offer(data, 2, TimeUnit.SECONDS)) {
                    System.out.println("放入数据失败：" + data);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                System.out.println("退出生产者线程！");
            }
        }
    }

    /**
     * 通过信号标志来停止当前线程
     */
    public void stop() {
        isRunning = false;
    }
}
