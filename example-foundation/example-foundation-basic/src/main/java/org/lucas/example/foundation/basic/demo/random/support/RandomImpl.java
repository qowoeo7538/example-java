package org.lucas.example.foundation.basic.demo.random.support;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class RandomImpl implements Runnable {

    private CyclicBarrier barrier;

    private String name;

    private Random random;

    private CountDownLatch cd;

    public RandomImpl(CyclicBarrier barrier, String name, Random random, CountDownLatch cd) {
        super();
        this.barrier = barrier;
        this.name = name;
        this.random = random;
        this.cd = cd;
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " 准备好了...");
            barrier.await();
            for (int j = 0; j < 10000; j++) {
                this.random.nextInt(50);
                // 1 高并发CAS锁竞争
                // 2 产生大量对象
                System.out.println(Thread.currentThread().getName() + ">" + this.random.nextInt(50));
            }
            cd.countDown();
        } catch (InterruptedException e) {
            System.out.println(name + " 中断异常！");
        } catch (BrokenBarrierException e) {
            System.out.println(name + " Barrier损坏异常！");
        }
    }

}
