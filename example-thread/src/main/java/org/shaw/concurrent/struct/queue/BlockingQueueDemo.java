package org.shaw.concurrent.struct.queue;

import org.junit.Test;
import org.shaw.concurrent.struct.queue.impl.Consumer;
import org.shaw.concurrent.struct.queue.impl.Producer;
import org.shaw.core.task.ExampleThreadExecutor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * 阻塞队列
 */
public class BlockingQueueDemo {

    /**
     * 生产者产出数据的速度大于消费者消费的速度，并且生产出来的数据累积到一定程度，
     * 那么生产者必须暂停等待（阻塞生产者线程），以便等待消费者线程把累积的数据处理完毕，反之亦然
     */
    @Test
    public void test() {
        // 声明一个容量为10的缓存队列
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(10);
        Producer producer1 = new Producer(queue);
        Producer producer2 = new Producer(queue);
        Producer producer3 = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        ExampleThreadExecutor.execute(producer1);
        ExampleThreadExecutor.execute(producer2);
        ExampleThreadExecutor.execute(producer3);
        ExampleThreadExecutor.execute(consumer);
        try {
            // 执行10s
            Thread.sleep(10 * 1000);
            producer1.stop();
            producer2.stop();
            producer3.stop();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            ExampleThreadExecutor.destroy();
        }
    }

}
