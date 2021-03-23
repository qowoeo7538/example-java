package org.lucas.example.framework.disruptor.demo.common;

import com.google.common.annotations.VisibleForTesting;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.LifecycleAware;
import com.lmax.disruptor.WorkHandler;

/**
 * @author TaoXi Plus Team
 * @since 0.0.1 on 2020-12-31
 */
public class TaskHandler
        implements EventHandler<TaskEvent<Runnable>>,
        WorkHandler<TaskEvent<Runnable>>,
        LifecycleAware {

    @Override
    public void onEvent(TaskEvent<Runnable> event, long sequence, boolean endOfBatch) {
        action(event);
    }

    @Override
    public void onStart() {
        //log.info("TaskHandler Start with thread [{}]", Thread.currentThread().getName());
    }

    @Override
    public void onShutdown() {
        // log.info("TaskHandler Shutdown with thread [{}]", Thread.currentThread().getName());
    }


    @Override
    public void onEvent(TaskEvent<Runnable> event) {
        action(event);
    }

    @VisibleForTesting
    void action(TaskEvent<Runnable> event) {
        try {
            if (event.getAction() != null) {
                event.getAction().run();
            }
        } finally {
            event.reset();
        }
    }
}
