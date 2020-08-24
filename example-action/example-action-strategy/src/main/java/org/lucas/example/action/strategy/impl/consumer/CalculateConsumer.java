package org.lucas.example.action.strategy.impl.consumer;

import com.lmax.disruptor.EventHandler;
import net.bytebuddy.matcher.FilterableList;

public class CalculateConsumer implements Consumer, EventHandler<FilterableList.Empty> {

    public static final String STRATEGY_NAME = "calculate";


    @Override
    public void onEvent(FilterableList.Empty event, long sequence, boolean endOfBatch) throws Exception {
        /*CompletableFuture<Integer> future = (CompletableFuture<Integer>) event.getFutureMap().get(STRATEGY_NAME);
        try {
            if (event.getModel() instanceof GeneralModel) {
                GeneralModel model = (GeneralModel) event.getModel();
                int a = (Integer) model.get("first");
                int b = (Integer) model.get("second");
                future.complete(a + b);
            }
        } catch (final Exception e) {
            future.completeExceptionally(e);
        }*/
    }

    @Override
    public String getName() {
        return STRATEGY_NAME;
    }
}
