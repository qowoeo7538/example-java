package org.lucas.example.action.strategy.impl;

import com.lmax.disruptor.RingBuffer;
import org.eclipse.collections.api.map.ImmutableMap;
import org.lucas.example.action.strategy.impl.event.AbstractStrategyEvent;

import java.util.concurrent.CompletableFuture;

public class StrategyEventPublisher {

    private final RingBuffer<AbstractStrategyEvent> ringBuffer;

    public StrategyEventPublisher(RingBuffer<AbstractStrategyEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void publish(ImmutableMap<String, CompletableFuture<?>> futureMap, StrategyModel model) {
        long sequence = ringBuffer.next();
        try {
            AbstractStrategyEvent event = ringBuffer.get(sequence);
            event.setModel(model);
            event.setFutureMap(futureMap);
        } finally {
            // todo
            ringBuffer.publish(sequence);
        }
    }

}
