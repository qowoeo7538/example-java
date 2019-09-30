package org.lucas.example.jvm.gc;

import org.junit.jupiter.api.Test;
import org.lucas.example.jvm.gc.impl.CleaningExample;

public class ClearnDemo {

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
