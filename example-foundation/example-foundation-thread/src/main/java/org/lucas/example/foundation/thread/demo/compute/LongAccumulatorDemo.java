package org.lucas.example.foundation.thread.demo.compute;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.function.LongBinaryOperator;

class LongAccumulatorDemo {

    @Test
    void demoLongAccumulator() throws InterruptedException {
        long start = System.currentTimeMillis();

        LongBinaryOperator lbn = (left, right) -> left + right * 2 + ThreadLocalRandom.current().nextInt(10);

        LongAccumulator la = new LongAccumulator(lbn, 1);
        System.out.println("初始值:" + la.longValue());

        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Thread t = new Thread(() -> la.accumulate(2));
            list.add(t);
        }


        for (Thread th : list) {
            th.start();
        }

        for (Thread th : list) {
            th.join();
        }

        long end = System.currentTimeMillis();

        System.out.println("耗时:" + (end - start) + ",KEVIN结果:" + la.longValue());
    }

}
