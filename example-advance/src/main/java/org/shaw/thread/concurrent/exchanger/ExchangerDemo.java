package org.shaw.thread.concurrent.exchanger;

import org.shaw.core.task.DefaultThreadFactory;
import org.shaw.thread.concurrent.exchanger.impl.ExchangerImpl;
import org.shaw.util.thread.DefaultThreadFactory;

import java.util.concurrent.Exchanger;

/**
 * Exchanger:线程之间的数据交换
 */
public class ExchangerDemo {

    public static void main(String[] args) {
        Exchanger<Integer> exchanger = new Exchanger<Integer>();
        ExchangerImpl.ExchangerProducer exchangerProducer = new ExchangerImpl().new ExchangerProducer(exchanger);
        ExchangerImpl.ExchangerConsumer exchangerConsumer = new ExchangerImpl().new ExchangerConsumer(exchanger);

        DefaultThreadFactory.execute(exchangerProducer);
        DefaultThreadFactory.execute(exchangerConsumer);
        DefaultThreadFactory.destroy();
    }
}
