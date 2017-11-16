package org.shaw.thread.concurrent.exchanger;

import org.shaw.thread.concurrent.exchanger.impl.ExchangerConsumer;
import org.shaw.thread.concurrent.exchanger.impl.ExchangerProducer;
import org.shaw.util.DefaultThreadFactory;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;

/**
 * Exchanger:线程之间的数据交换
 */
public class ExchangerDemo {

    public static Boolean isDone = false;

    public static void main(String[] args) {
        ExecutorService executorService = DefaultThreadFactory.getThreadPoolExecutor();
        Exchanger<Integer> exchanger = new Exchanger<>();
        ExchangerProducer exchangerProducer = new ExchangerProducer(exchanger, isDone);
        ExchangerConsumer exchangerConsumer = new ExchangerConsumer(exchanger, isDone);

        executorService.execute(exchangerProducer);
        executorService.execute(exchangerConsumer);
        DefaultThreadFactory.destroy();
    }
}
