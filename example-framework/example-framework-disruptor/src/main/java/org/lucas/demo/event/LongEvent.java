package org.lucas.demo.event;

/**
 * 定义一个事件
 */
public class LongEvent {
    private long value;

    public void set(long value) {
        this.value = value;
    }

    public long get() {
        return value;
    }
}