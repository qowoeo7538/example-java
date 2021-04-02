package org.lucas.example.foundation.thread.demo.cyclicbarrier.support;

import org.lucas.example.foundation.core.util.DataProducerHelper;

import java.util.concurrent.CyclicBarrier;

/**
 * @create: 2017-11-15
 * @description:
 */
public class BarrierThread implements Runnable {

    CyclicBarrier cyclicBarrier;

    String name;

    public BarrierThread(String name, CyclicBarrier cyclicBarrier) {
        // 一个线程作用范围是整个调用链
        this.name = name;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            Thread.currentThread().setName(name);
            Thread.sleep(DataProducerHelper.nextInt(0, 10000));
            System.out.println(Thread.currentThread().getName() + "即将到达集合地点1，当前已有" + cyclicBarrier.getNumberWaiting() + "个已经到达，正在等候");
            cyclicBarrier.await();
            Thread.sleep(DataProducerHelper.nextInt(0, 10000));
            System.out.println(Thread.currentThread().getName() + "即将到达集合地点2，当前已有" + cyclicBarrier.getNumberWaiting() + "个已经到达，正在等候");
            cyclicBarrier.await();
            Thread.sleep(DataProducerHelper.nextInt(0, 10000));
            System.out.println(Thread.currentThread().getName() + "即将到达集合地点3，当前已有" + cyclicBarrier.getNumberWaiting() + "个已经到达，正在等候");
            cyclicBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
