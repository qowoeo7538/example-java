package org.lucas.example.framework.disruptor.demo.producer;

import com.lmax.disruptor.RingBuffer;
import org.lucas.example.framework.disruptor.demo.event.LongEvent;

/**
 * LongEvent 事件发布者
 */
public class LongEventProducer {

    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /**
     * 发布消息的两阶段
     * 第一阶段：获取 RingBuffer 槽中的对象并修改。
     * 第二阶段：发布可用数据。
     * <p>
     * 注意：必须将发布包装在 try/finally 块中，如果在 RingBuffer 中申明
     * 一个槽（调用 RingBuffer.next()），那么必须发布这个序列，否则可能会
     * 导致序列状态被污染。
     */
    public void onData(long bb) {
        // 1 第一阶段，获取序列号
        long sequence = ringBuffer.next();
        try {
            // 2 获取序列号对应的事件对象
            LongEvent event = ringBuffer.get(sequence);
            // 3 修改元素的值
            event.set(bb);
        } finally {
            // 4 发布元素。
            ringBuffer.publish(sequence);
        }
    }
}