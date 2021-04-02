package org.lucas.example.foundation.io.kata.longpolling.support;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class RequestSuspendUtil {

    private final Sync sync;

    public RequestSuspendUtil(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("[RequestSuspendUtil]初始化count必须大于0");
        }
        this.sync = new Sync(count);
    }

    public void reset() {
        sync.reset();
    }

    public boolean await(long timeout, TimeUnit unit)
            throws InterruptedException {
        return sync.tryAcquireSharedNanos(1, unit.toNanos(timeout));
    }

    private static final class Sync extends AbstractQueuedSynchronizer {

        private static final long serialVersionUID = 4298972817744960765L;

        private final int startCount;

        Sync(int count) {
            this.startCount = count;
            setState(count);
        }

        protected void reset() {
            setState(startCount);
        }

    }

}
