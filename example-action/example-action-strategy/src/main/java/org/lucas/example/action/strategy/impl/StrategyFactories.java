package org.lucas.example.action.strategy.impl;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.impl.map.mutable.ConcurrentHashMap;
import org.lucas.example.action.strategy.impl.consumer.CalculateConsumer;
import org.lucas.example.action.strategy.impl.consumer.Consumer;
import org.lucas.example.action.strategy.impl.event.StrategyEvent;

import java.util.Map;

public class StrategyFactories {

    private Map<String, StrategyEventPublisher> publisherMap = new ConcurrentHashMap<>();

    public StrategyEventPublisher getStrategy(String strategyName) {
        return publisherMap.get(strategyName);
    }

    public void register(ImmutableList<Consumer> consumers) {
        for (Consumer consumer : consumers) {
            StrategyEventFactory factory = new StrategyEventFactory();
            Disruptor<StrategyEvent> disruptor = new Disruptor<>(factory, consumer.getBufferSize(),
                    DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());
            disruptor.handleEventsWith(new CalculateConsumer());
            disruptor.start();
            RingBuffer<StrategyEvent> ringBuffer = disruptor.getRingBuffer();
            StrategyEventPublisher publisher = new StrategyEventPublisher(ringBuffer);
            publisherMap.put(consumer.getName(), publisher);
        }
    }
}
