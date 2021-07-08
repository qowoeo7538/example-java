package org.lucas.example.foundation.thread.demo.signal;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.core.task.ExampleThreadExecutor;
import org.lucas.example.foundation.thread.demo.signal.support.ExchangerImpl;

import java.util.concurrent.Exchanger;

/**
 * Exchanger:线程之间的数据交换
 */
public class ExchangerDemo {

    @Test
    public void demoExchanger() {
        Exchanger<Integer> exchanger = new Exchanger<Integer>();
        ExchangerImpl.ExchangerProducer exchangerProducer = new ExchangerImpl().new ExchangerProducer(exchanger);
        ExchangerImpl.ExchangerConsumer exchangerConsumer = new ExchangerImpl().new ExchangerConsumer(exchanger);

        ExampleThreadExecutor.execute(exchangerProducer);
        ExampleThreadExecutor.execute(exchangerConsumer);
        ExampleThreadExecutor.destroy();
    }
}
