package org.lucas.example.foundation.basic.demo.struct.set;

import org.junit.jupiter.api.Test;

public class CopyOnWriteArraySetDemo {

    /**
     * 相比CopyOnWriteArrayList的 add() 性能较低，因为底层就是一个
     * CopyOnWriteArrayList.addIfAbsent来实现，通过一个循环查找来判断当前值是否唯一。
     */
    @Test
    public void demoAdd() {

    }

}
