package org.lucas.example.foundation.basic.demo.struct.concurrent.set;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CopyOnWriteArraySet;

public class CopyOnWriteArraySetDemo {

    /**
     * 相比CopyOnWriteArrayList的 add() 性能较低，因为底层就是一个
     * CopyOnWriteArrayList.addIfAbsent来实现，通过一个循环查找来判断当前值是否唯一。
     */
    @Test
    public void demoAdd() {
        final CopyOnWriteArraySet<Integer> set = new CopyOnWriteArraySet<Integer>();
        set.add(2);
        set.add(3);
        set.add(1);
        set.add(4);
        System.out.println(set);
        set.add(2);
        set.add(3);
        set.add(4);
        System.out.println(set);
    }

}
