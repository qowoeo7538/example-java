package org.lucas.example.foundation.thread.demo.compute;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

class LongAdderDemo {

    @Test
    void demoIncrementAndGet() throws InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(100);
        AtomicLong al = new AtomicLong();
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(() -> {
                try {
                    barrier.await();
                    for(int i1 = 0; i1 <10000; i1++) {
                        al.incrementAndGet();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
            list.add(t);
        }
        long start = System.currentTimeMillis();
        for (Thread th : list) {
            th.start();
        }
        for (Thread th : list) {
            th.join();
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时:" + (end - start) + "ms, sum:" + al.get());
    }

    @Test
    void demoIncrement() throws InterruptedException{
        CyclicBarrier barrier = new CyclicBarrier(100);
        LongAdder longAdder = new LongAdder();
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(() -> {
                try {
                    barrier.await();
                    for(int i1 = 0; i1 <10000; i1++) {
                        longAdder.increment();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
            list.add(t);
        }

        long start = System.currentTimeMillis();
        for (Thread th : list) {
            th.start();
        }
        for (Thread th : list) {
            th.join();
        }
        long end = System.currentTimeMillis();

        System.out.println("耗时:" + (end - start) + "ms, sum:" + longAdder.sum());
    }

}
