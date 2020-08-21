package org.lucas.example.action.strategy;

import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Maps;
import org.junit.jupiter.api.Test;
import org.lucas.example.action.strategy.impl.StrategyEventPublisher;
import org.lucas.example.action.strategy.impl.StrategyFactories;
import org.lucas.example.action.strategy.impl.consumer.CalculateConsumer;
import org.lucas.example.action.strategy.impl.model.GeneralModel;

import java.util.concurrent.CompletableFuture;

public class StrategyTests {

    @Test
    public void testStrategy() throws Exception {
        StrategyFactories factories = new StrategyFactories();
        factories.register(Lists.immutable.of(new CalculateConsumer()));

        CompletableFuture<Integer> calculateFuture = new CompletableFuture<>();
        ImmutableMap<String, CompletableFuture<?>> futureMap = Maps.immutable
                .with(CalculateConsumer.STRATEGY_NAME, calculateFuture);

        StrategyEventPublisher calculatePublisher = factories.getStrategy(CalculateConsumer.STRATEGY_NAME);
        GeneralModel model = new GeneralModel();
        model.put("first", 22);
        model.put("second", 33);
        calculatePublisher.publish(futureMap, model);
        System.out.println(calculateFuture.get());
    }

}
