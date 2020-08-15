package org.lucas.demo.consumer;

import com.lmax.disruptor.EventHandler;
import org.lucas.demo.event.LongEvent;

/**
 * LongEvent 事件消费者三号
 */
public class ApplicationConsumer implements EventHandler<LongEvent> {

	@Override
	public void onEvent(LongEvent event, long sequence, boolean endOfBatch) {
		System.out.println(Thread.currentThread().getName() + "Application Event: " + event.get());
	}
}