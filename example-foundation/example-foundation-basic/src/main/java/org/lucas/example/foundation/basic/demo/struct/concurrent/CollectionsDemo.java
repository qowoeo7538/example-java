package org.lucas.example.foundation.basic.demo.struct.concurrent;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.core.task.ExampleThreadExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionsDemo {

    @Test
    public void synchronizeds() {
        /*
         * 使用以下方法被包裹的类将支持多线程
         * Collections.synchronizedCollection(c);
         * Collections.synchronizedList(list);
         * Collections.synchronizedMap(m);
         * Collections.synchronizedSet(s);
         * Collections.synchronizedSortedMap(m);
         * Collections.synchronizedSortedSet(s);
         */

        // 线程安全的List
        final List<String> list = Collections.synchronizedList(new ArrayList<String>());

        //向list中并发加入1万个元素,如果是线程安全的那么list.size=1万,否则!=1万
        for (int i = 0; i < 10000; i++) {
            ExampleThreadExecutor.execute(() -> list.add("5"));
        }
        ExampleThreadExecutor.destroy();
        if (list.size() != 10000) {
            System.out.println("线程不安全！");
        } else {
            System.out.println("线程安全!");
        }
    }

}
