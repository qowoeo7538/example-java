package org.lucas.example.framework.disruptor.demo.common;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.InsufficientCapacityException;
import com.lmax.disruptor.LiteTimeoutBlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 任务分发的具体实现,目前默认等待策略采用{@link LiteTimeoutBlockingWaitStrategy}
 *
 * @author TaoXi Plus Team
 * @since 0.0.1 on 2020-12-31
 */
public class AsyncTaskDispatcher implements Dispatcher<Runnable> {

    private Disruptor<TaskEvent<Runnable>> dispatcher;

    private static final long INVALID_SEQUENCE = Long.MIN_VALUE;

    public AsyncTaskDispatcher(int workers, int bufferSize) {
        this(
                workers,
                bufferSize,
                new ThreadFactoryBuilder().setNameFormat("task-dispatcher-%s").setDaemon(true).build(),
                new LiteTimeoutBlockingWaitStrategy(1000, TimeUnit.MILLISECONDS));
    }

    public AsyncTaskDispatcher(
            int workers, int bufferSize, ThreadFactory factory, WaitStrategy waitStrategy) {
        var ringBufferSize = Math.min(findNextPositivePowerOfTwo(bufferSize), 256 * 1024);
        var threads = Math.max(workers, 1);
        EventFactory<TaskEvent<Runnable>> eventFactory = TaskEvent::new;
        this.dispatcher =
                new Disruptor<>(eventFactory, ringBufferSize, factory, ProducerType.MULTI, waitStrategy);
        this.dispatcher.setDefaultExceptionHandler(LoggingExceptionHandler.INSTANCE);
        if (threads == 1) {
            this.dispatcher.handleEventsWith(new TaskHandler());
        } else {
            var handlers = new TaskHandler[threads];
            for (int i = 0; i < threads; i++) {
                handlers[i] = new TaskHandler();
            }
            this.dispatcher.handleEventsWithWorkerPool(handlers);
        }
        this.dispatcher.start();
    }

    @Override
    public boolean dispatch(@NonNull Runnable task) {
        final RingBuffer<TaskEvent<Runnable>> ringBuffer = this.dispatcher.getRingBuffer();
        var next = INVALID_SEQUENCE;
        try {
            next = ringBuffer.tryNext();
            var taskEvent = ringBuffer.get(next);
            taskEvent.setAction(task);
            return true;
        } catch (InsufficientCapacityException e) {
            // this throw by ringBuffer ignore
            return false;
        } finally {
            if (next != INVALID_SEQUENCE) {
                ringBuffer.publish(next);
            }
        }
    }

    @Override
    public void shutdown() {
        final Disruptor<TaskEvent<Runnable>> temp = this.dispatcher;
        // 让原来的分发器为null，新任务提交时回触发空指针异常拒绝新任务的提交
        this.dispatcher = null;
        temp.shutdown();
    }

    @Override
    public void execute(@NonNull Runnable command) {
        if (!dispatch(command)) {
            // 当RingBuffer满时采用当前线程直接运行任务，让上游慢下来
            command.run();
        }
    }

    public static int findNextPositivePowerOfTwo(final int value) {
        if (value <= 0) {
            return 1;
        }
        if (value >= 0x40000000) {
            return 0x40000000;
        }
        return 1 << (32 - Integer.numberOfLeadingZeros(value - 1));
    }
}
