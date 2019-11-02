package org.lucas.example.foundation.io.kata.longpolling.impl;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class ServiceThread implements Runnable {

    protected final RequestSuspendUtil waitPoint = new RequestSuspendUtil(1);

    protected volatile AtomicBoolean hasNotified = new AtomicBoolean(false);

    protected void waitForRunning(long interval) {
        if (hasNotified.compareAndSet(true, false)) {
            this.onWaitEnd();
            return;
        }
        waitPoint.reset();
        try {
            waitPoint.await(interval, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
        } finally {
            hasNotified.set(false);
            this.onWaitEnd();
        }
    }

    protected void onWaitEnd() {
    }

}
