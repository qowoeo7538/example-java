package org.lucas.example.thread.demo.exchanger;

import org.lucas.example.thread.demo.exchanger.impl.ExchangerImpl;
import org.lucas.example.core.task.ExampleThreadExecutor;

import java.util.concurrent.Exchanger;

/**
 * Exchanger:线程之间的数据交换
 */
public class ExchangerDemo {

    public static void main(String[] args) {
        Exchanger<Integer> exchanger = new Exchanger<Integer>();
        ExchangerImpl.ExchangerProducer exchangerProducer = new ExchangerImpl().new ExchangerProducer(exchanger);
        ExchangerImpl.ExchangerConsumer exchangerConsumer = new ExchangerImpl().new ExchangerConsumer(exchanger);

        ExampleThreadExecutor.execute(exchangerProducer);
        ExampleThreadExecutor.execute(exchangerConsumer);
        ExampleThreadExecutor.destroy();
    }
}
