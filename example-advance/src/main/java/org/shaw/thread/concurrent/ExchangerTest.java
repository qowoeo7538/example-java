package org.shaw.thread.concurrent;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by joy on 17-2-24.
 */
public class ExchangerTest {
    private static volatile boolean _isDone = false;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Exchanger<Integer> exchanger = new Exchanger<Integer>();
        ExchangerProducer exchangerProducer = new ExchangerProducer(exchanger);
        ExchangerConsumer exchangerConsumer = new ExchangerConsumer(exchanger);

        executorService.execute(exchangerProducer);
        executorService.execute(exchangerConsumer);
        executorService.shutdown();
        try {
            executorService.awaitTermination(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static class ExchangerProducer implements Runnable {
        private Exchanger<Integer> exchanger;
        private static int data = 1;

        ExchangerProducer(Exchanger<Integer> exchanger) {
            this.exchanger = exchanger;
        }

        public void run() {
            while (!Thread.interrupted() && !_isDone) {
                for (int i = 1; i <= 3; i++) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        data = i;
                        System.out.println("producer change before: " + data);
                        data = exchanger.exchange(data);
                        System.out.println("producer change after: " + data);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                _isDone = true;
            }
        }
    }

    static class ExchangerConsumer implements Runnable {
        private Exchanger<Integer> exchanger;
        private static int data = 0;

        ExchangerConsumer(Exchanger<Integer> exchanger) {
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            while (!Thread.interrupted() && !_isDone) {
                data = 0;
                System.out.println("consumer change before : " + data);
                try {
                    TimeUnit.SECONDS.sleep(1);
                    data = exchanger.exchange(data);
                    System.out.println("consumer change after : " + data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

