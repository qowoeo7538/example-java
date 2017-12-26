package org.shaw.base.concurrent.exchanger;

import org.shaw.concurrent.exchanger.impl.ExchangerImpl;
import org.shaw.core.task.StandardThreadExecutor;

import java.util.concurrent.Exchanger;

/**
 * Exchanger:线程之间的数据交换
 */
public class ExchangerDemo {

    public static void main(String[] args) {
        Exchanger<Integer> exchanger = new Exchanger<Integer>();
        ExchangerImpl.ExchangerProducer exchangerProducer = new ExchangerImpl().new ExchangerProducer(exchanger);
        ExchangerImpl.ExchangerConsumer exchangerConsumer = new ExchangerImpl().new ExchangerConsumer(exchanger);

        StandardThreadExecutor.execute(exchangerProducer);
        StandardThreadExecutor.execute(exchangerConsumer);
        StandardThreadExecutor.destroy();
    }
}