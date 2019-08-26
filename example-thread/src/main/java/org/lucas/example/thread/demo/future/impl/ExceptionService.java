package org.lucas.example.thread.demo.future.impl;

import org.junit.jupiter.api.Assertions;

import java.util.concurrent.Callable;

public class ExceptionService implements Callable<Boolean> {

    @Override
    public Boolean call() throws Exception {
        Assertions.fail("任务异常！");
        return true;
    }
}
