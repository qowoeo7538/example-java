package org.lucas.example.foundation.basic.demo.random.support;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomImpl implements Runnable  {

    private CyclicBarrier barrier;

    private String name;

    private CountDownLatch cd;

    public ThreadLocalRandomImpl(CyclicBarrier barrier, String name,CountDownLatch cd) {
        super();
        this.barrier = barrier;
        this.name = name;
        this.cd = cd;
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " 准备好了...");
            barrier.await();
            for(int j=0;j<10000;j++) {
                ThreadLocalRandom.current().nextInt(50);
            }
            cd.countDown();
        } catch (InterruptedException e) {
            System.out.println(name + " 中断异常！");
        } catch (BrokenBarrierException e) {
            System.out.println(name + " Barrier损坏异常！");
        }
    }
}
