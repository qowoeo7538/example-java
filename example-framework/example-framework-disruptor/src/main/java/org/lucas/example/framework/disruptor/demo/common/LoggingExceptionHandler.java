package org.lucas.example.framework.disruptor.demo.common;

import com.lmax.disruptor.ExceptionHandler;

/**
 * @author TaoXi Plus Team
 * @since 0.0.1 on 2020-12-31
 */

public enum LoggingExceptionHandler implements ExceptionHandler<Object> {
    INSTANCE;

    @Override
    public void handleEventException(Throwable ex, long sequence, Object event) {
        // log.error("Exception processing: {} {}, {}.", sequence, event, Throwables.getStackTraceAsString(ex));
    }

    @Override
    public void handleOnStartException(Throwable ex) {
        // log.warn("Exception during handleOnStartException(), {}.", Throwables.getStackTraceAsString(ex));
    }

    @Override
    public void handleOnShutdownException(Throwable ex) {
        // log.warn("Exception during handleOnShutdownException(), {}.", Throwables.getStackTraceAsString(ex));
    }
}
