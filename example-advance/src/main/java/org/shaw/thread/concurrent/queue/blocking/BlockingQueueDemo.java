package org.shaw.thread.concurrent.queue.blocking;

import org.shaw.thread.concurrent.queue.blocking.impl.Consumer;
import org.shaw.thread.concurrent.queue.blocking.impl.Producer;

import java.util.concurrent.*;

/**
 * 阻塞队列
 * <p>
 * 生产者产出数据的速度大于消费者消费的速度，并且生产出来的数据累积到一定程度，
 * 那么生产者必须暂停等待（阻塞生产者线程），以便等待消费者线程把累积的数据处理完毕，反之亦然
 */
public class BlockingQueueDemo {

    public static void main(String[] args) {
        // 声明一个容量为10的缓存队列
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(10);

        Producer producer1 = new Producer(queue);
        Producer producer2 = new Producer(queue);
        Producer producer3 = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        // 借助Executors
        ExecutorService service = Executors.newCachedThreadPool();
        // 启动线程
        service.execute(producer1);
        service.execute(producer2);
        service.execute(producer3);
        service.execute(consumer);
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
            // 退出Executor
            service.shutdown();
            try {
                service.awaitTermination(2, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}
