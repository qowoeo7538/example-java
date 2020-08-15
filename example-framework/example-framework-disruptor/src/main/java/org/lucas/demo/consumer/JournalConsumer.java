package org.lucas.demo.consumer;

import com.lmax.disruptor.EventHandler;
import org.lucas.demo.event.LongEvent;

/**
 * LongEvent 事件消费者一号
 */
public class JournalConsumer implements EventHandler<LongEvent> {

    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) {
        System.out.println(Thread.currentThread().getName() + "Persist Event: " + event.get());
    }

}