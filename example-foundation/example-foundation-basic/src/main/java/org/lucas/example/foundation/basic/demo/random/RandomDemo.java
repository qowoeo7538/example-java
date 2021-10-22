package org.lucas.example.foundation.basic.demo.random;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.basic.demo.random.support.RandomImpl;
import org.lucas.example.foundation.basic.demo.random.support.ThreadLocalRandomImpl;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class RandomDemo {

    @Test
    void demoRandom() throws InterruptedException {
        long start = System.currentTimeMillis();

        Random random = new Random();
        CountDownLatch cd = new CountDownLatch(100);
        CyclicBarrier barrier = new CyclicBarrier(100);
        ExecutorService executor = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100; i++) {
            executor.submit(new RandomImpl(barrier, "thread" + i, random, cd));
        }
        cd.await();

        long use = System.currentTimeMillis() - start;

        System.out.println("main is over.." + use);

        executor.shutdown();
    }

    @Test
    void demoThreadLocalRandom() throws InterruptedException {
        long start = System.currentTimeMillis();
        CountDownLatch cd = new CountDownLatch(100);
        CyclicBarrier barrier = new CyclicBarrier(100);
        ExecutorService executor = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100; i++) {
            executor.submit(new ThreadLocalRandomImpl(barrier, "thread" + i, cd));
        }
        cd.await();
        long use = System.currentTimeMillis() - start;
        System.out.println("main is over.." + use);
        executor.shutdown();
    }
}
