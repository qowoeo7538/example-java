package org.shaw.kata.gc;

import org.junit.Test;
import org.shaw.kata.gc.impl.CleaningExample;

public class ClearnKata {

    /**
     * 每次创建对象时，都会打印init;回收对象时，都会打印clean.
     */
    @Test
    public void test() {
        while (true) {
            new CleaningExample();
        }
    }
}
