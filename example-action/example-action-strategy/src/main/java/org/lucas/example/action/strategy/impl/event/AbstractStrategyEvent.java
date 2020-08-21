package org.lucas.example.action.strategy.impl.event;

import org.eclipse.collections.api.map.ImmutableMap;
import org.lucas.example.action.strategy.impl.StrategyModel;
import org.lucas.example.action.strategy.impl.consumer.Consumer;

import java.util.concurrent.CompletableFuture;

public abstract class AbstractStrategyEvent<T extends Consumer> {

    private ImmutableMap<String, CompletableFuture<?>> futureMap;

    private StrategyModel model;

    public T consumer;

    public ImmutableMap<String, CompletableFuture<?>> getFutureMap() {
        return futureMap;
    }

    public void setFutureMap(ImmutableMap<String, CompletableFuture<?>> futureMap) {
        this.futureMap = futureMap;
    }

    public StrategyModel getModel() {
        return model;
    }

    public void setModel(StrategyModel model) {
        this.model = model;
    }
}
