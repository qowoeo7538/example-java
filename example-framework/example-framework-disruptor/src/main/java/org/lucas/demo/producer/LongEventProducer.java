package org.lucas.demo.producer;

import com.lmax.disruptor.RingBuffer;
import org.lucas.demo.event.LongEvent;

/**
 * LongEvent 事件发布者
 */
public class LongEventProducer {

    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(long bb) {
        // 1 第一阶段，获取序列号
        long sequence = ringBuffer.next();
        try {
            // 2 获取序列号对应的实体元素
            LongEvent event = ringBuffer.get(sequence);
            // 3 修改元素的值
            event.set(bb);
        } finally {
            // 4 发布元素。
            ringBuffer.publish(sequence);
        }
    }
}