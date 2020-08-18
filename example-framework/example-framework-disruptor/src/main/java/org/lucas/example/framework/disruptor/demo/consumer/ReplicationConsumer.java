package org.lucas.example.framework.disruptor.demo.consumer;

import com.lmax.disruptor.EventHandler;
import org.lucas.example.framework.disruptor.demo.event.LongEvent;

/**
 * LongEvent 事件消费者二号
 */
public class ReplicationConsumer implements EventHandler<LongEvent> {

    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) {
        System.out.println(Thread.currentThread().getName() + "Replication Event: " + event.get());
    }
}