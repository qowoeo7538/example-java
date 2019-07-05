package org.lucas.kata.safe;

import org.junit.jupiter.api.Test;
import org.lucas.example.core.task.ExampleThreadExecutor;

import java.util.ArrayList;
import java.util.List;

public class ArrayListKata {

    @Test
    public void concurrent() {
        // 初始化一个list，放入5个元素
        final List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            list.add(i);
        }

        // 线程一：通过Iterator遍历List
        ExampleThreadExecutor.execute(() -> {
            for (int item : list) {
                System.out.println("遍历元素：" + item);
                // 由于程序跑的太快，这里sleep了1秒来调慢程序的运行速度
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 线程二：remove一个元素
        ExampleThreadExecutor.execute(() -> {
            // 由于程序跑的太快，这里sleep了1秒来调慢程序的运行速度
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list.remove(4);
            System.out.println("list.remove(4)");
        });

        ExampleThreadExecutor.destroy();
    }

}
