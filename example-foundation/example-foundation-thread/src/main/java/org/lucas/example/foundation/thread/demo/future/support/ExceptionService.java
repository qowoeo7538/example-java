package org.lucas.example.foundation.thread.demo.future.support;

import java.util.concurrent.Callable;

public class ExceptionService implements Callable<Boolean> {

    @Override
    public Boolean call() throws Exception {
        throw new RuntimeException("任务异常！");
    }
}
