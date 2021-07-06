package org.lucas.example.foundation.basic.demo.struct.concurrent.map;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.basic.kata.generics.support.Child;
import org.lucas.example.foundation.core.task.ExampleThreadExecutor;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;

public class MapDemo {

    @Test
    public void demoAllMap() throws Exception {
        final var map = new ConcurrentHashMap<>();
        // testMap(map);

        //高性能线程安全且有序
        final var skipMap = new ConcurrentSkipListMap<>();
        // testMap(skipMap);

        //低性能线程安全
        final var sortedMap = Collections.synchronizedSortedMap(new TreeMap<>());
        testMap(sortedMap);

        ExampleThreadExecutor.destroy();
    }

    private void testMap(Map map) throws Exception {
        final var countDownLatch = new CountDownLatch(10);
        for (int k = 0; k < 10; k++) {
            ExampleThreadExecutor.execute(() -> {
                Long start = System.currentTimeMillis();
                for (int i = 0; i < 1000; i++) {
                    try {
                        map.put("k" + i % 10, new Child());
                        Thread.sleep(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("添加100个元素耗时：" + (System.currentTimeMillis() - start) + "毫秒");
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        System.out.println(map.size());
    }

}
