package org.shaw.kata.keyword.impl;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @create: 2017-11-21
 * @description:
 */
public class StaticAttributeTest {

    private AtomicInteger obj;

    private int concurrencyCount;

    public StaticAttributeTest(AtomicInteger obj, int concurrencyCount) {
        this.obj = obj;
        this.concurrencyCount = concurrencyCount;
    }

    public void setConcurrencyCount(int concurrencyCount) {
        this.concurrencyCount = concurrencyCount;
    }

    public AtomicInteger getObj() {
        return obj;
    }
}
