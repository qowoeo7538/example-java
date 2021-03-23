package org.lucas.example.framework.disruptor.demo.common;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.Executor;

/**
 * 异步任务分发器，使用于耗时短/计算密集型的场景
 *
 * @author TaoXi Plus Team
 * @since 0.0.1 on 2020-12-31
 */
public interface Dispatcher<T> extends Executor {

    /**
     * dispatch a task to worker
     *
     * <p>当调用该方法后再调度任务时回跑出{@link NullPointerException}
     *
     * @param task the task action
     * @return <code>true</code> if dispatch success other else false
     */
    boolean dispatch(final @NonNull T task);

    /**
     * shutdown the dispatcher
     *
     * @throws NullPointerException when the dispatch is shutdown
     */
    void shutdown();
}
