package org.lucas.example.action.strategy.impl;

import com.lmax.disruptor.EventFactory;
import net.bytebuddy.matcher.FilterableList;

public class StrategyEventFactory implements EventFactory<FilterableList.Empty> {

    @Override
    public FilterableList.Empty newInstance() {
        return new FilterableList.Empty();
    }

}
