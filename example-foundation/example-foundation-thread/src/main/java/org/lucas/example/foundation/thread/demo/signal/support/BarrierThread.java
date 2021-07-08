package org.lucas.example.foundation.thread.demo.signal.support;

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
            // barrier的await方法，在所有参与者都已经在此 barrier 上调用 await 方法之前，将一直等待。
            cyclicBarrier.await();
            Thread.sleep(DataProducerHelper.nextInt(0, 10000));
            System.out.println(Thread.currentThread().getName() + "即将到达集合地点2，当前已有" + cyclicBarrier.getNumberWaiting() + "个已经到达，正在等候");
            // barrier的await方法，在所有参与者都已经在此 barrier 上调用 await 方法之前，将一直等待。
            cyclicBarrier.await();
            Thread.sleep(DataProducerHelper.nextInt(0, 10000));
            System.out.println(Thread.currentThread().getName() + "即将到达集合地点3，当前已有" + cyclicBarrier.getNumberWaiting() + "个已经到达，正在等候");
            // barrier的await方法，在所有参与者都已经在此 barrier 上调用 await 方法之前，将一直等待。
            cyclicBarrier.await();

            //设置等待时间,如果等待了1秒,最后一个线程还没有就位,则自己继续运行,但是会导致Barrier被标记为一个已经破坏的Barrier
            //barrier.await(1,TimeUnit.SECONDS);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
