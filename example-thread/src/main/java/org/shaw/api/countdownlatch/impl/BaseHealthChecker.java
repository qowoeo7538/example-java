package org.shaw.api.countdownlatch.impl;

import java.util.concurrent.CountDownLatch;

/**
 * @create: 2017-11-06
 * @description:
 */
public abstract class BaseHealthChecker implements Runnable {

    private CountDownLatch _latch;

    private boolean _serviceUp;

    private String _serviceName;

    public BaseHealthChecker(String serviceName, CountDownLatch latch) {
        this._latch = latch;
        this._serviceName = serviceName;
        this._serviceUp = false;
    }

    @Override
    public void run() {
        try {
            verifyService();
            _serviceUp = true;
        } catch (Throwable t) {
            t.printStackTrace(System.err);
            _serviceUp = false;
        } finally {
            if (_latch != null) {
                _latch.countDown();
            }
        }
    }

    public boolean isServiceUp() {
        return _serviceUp;
    }

    public String getserviceName() {
        return this._serviceName;
    }

    public abstract void verifyService();
}
