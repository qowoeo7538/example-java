package org.lucas.example.foundation.thread.kata.queue;

import org.lucas.example.foundation.core.task.ExampleThreadExecutor;
import org.lucas.example.foundation.thread.kata.queue.support.Consumer;
import org.lucas.example.foundation.thread.kata.queue.support.Data;
import org.lucas.example.foundation.thread.kata.queue.support.Provider;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueKata {

    public static void main(String[] args) {

        //消息队列
        BlockingQueue<Data> queue = new LinkedBlockingQueue<>(10);

        // 生产者
        Provider p1 = new Provider("p1", queue);
        Provider p2 = new Provider("p2", queue);
        Provider p3 = new Provider("p3", queue);

        // 消费者
        Consumer c1 = new Consumer("c1", queue);
        Consumer c2 = new Consumer("c2", queue);
        Consumer c3 = new Consumer("c3", queue);
        Consumer c4 = new Consumer("c4", queue);
        Consumer c5 = new Consumer("c5", queue);
        Consumer c6 = new Consumer("c6", queue);
        Consumer c7 = new Consumer("c7", queue);

        ExampleThreadExecutor.execute(p1);
        ExampleThreadExecutor.execute(p2);
        ExampleThreadExecutor.execute(p3);
        ExampleThreadExecutor.execute(c1);
        ExampleThreadExecutor.execute(c2);
        ExampleThreadExecutor.execute(c3);
        ExampleThreadExecutor.execute(c4);
        ExampleThreadExecutor.execute(c5);
        ExampleThreadExecutor.execute(c6);
        ExampleThreadExecutor.execute(c7);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //生产者停止产生数据
        p1.stop();
        p2.stop();
        p3.stop();

        ExampleThreadExecutor.destroy();
    }

}
