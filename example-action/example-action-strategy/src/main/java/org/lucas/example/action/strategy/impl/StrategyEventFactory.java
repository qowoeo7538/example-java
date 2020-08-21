package org.lucas.example.action.strategy.impl;

import com.lmax.disruptor.EventFactory;
import org.lucas.example.action.strategy.impl.event.StrategyEvent;

public class StrategyEventFactory implements EventFactory<StrategyEvent> {

    @Override
    public StrategyEvent newInstance() {
        return new StrategyEvent();
    }

}
