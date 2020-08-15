package org.lucas.demo;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.lucas.demo.consumer.ApplicationConsumer;
import org.lucas.demo.consumer.JournalConsumer;
import org.lucas.demo.consumer.ReplicationConsumer;
import org.lucas.demo.event.LongEvent;
import org.lucas.demo.event.LongEventFactory;
import org.lucas.demo.producer.LongEventProducer;

public class LongEventDemo {

    public static void main(String[] args) throws Exception {
        // 1 创建Ring Buffer中事件元素的工厂对象
        LongEventFactory factory = new LongEventFactory();

        // 2 指定Ring Buffer的大小,必须为2的幂次方.
        int bufferSize = 1024;

        // 3 构造Disruptor,其构造函数会根据 bufferSize 大小调用 LongEventFactory 创建对应个数的事件
        // 对象（事件预分配），并且这里使用 DaemonThreadFactory.INSTANCE 作为其背后异步任务调用的线程
        // 池（可以自定义），另外，因为只有一个生产者，所以可以设置生产者模式为 ProducerType.SINGLE 以
        // 便遵循 Single Writer 原则，减少 CAS 操作，最后设置 RingBuffer 的等待策略为 BlockingWaitStrategy。
        Disruptor<LongEvent> disruptor = new Disruptor<>(factory, bufferSize, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());

        // 4 注册消费者，同一个元素被 JournalConsumer 和 ReplicationConsumer 消费后，
        // ApplicationConsumer 才可以消费对应的元素。
        disruptor.handleEventsWith(new JournalConsumer(), new ReplicationConsumer()).then(new ApplicationConsumer());

        // 5 启动 disruptor 线程运行
        disruptor.start();

        // 6 从 Disruptor 中获取 RingBuffer，通过 RingBuffer 向 Disruptor 写入事件。
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        // 7 创建生产者，并把 RingBuffer 作为构造函数参数。
        LongEventProducer producer = new LongEventProducer(ringBuffer);

        // 8 生产元素，并放入RingBuffer
        for (long l = 0; l < 100; l++) {
            // 8.1 发布事件
            producer.onData(l);
            Thread.sleep(1000);
        }

        // 9 挂起当前线程
        Thread.currentThread().join();

    }
}