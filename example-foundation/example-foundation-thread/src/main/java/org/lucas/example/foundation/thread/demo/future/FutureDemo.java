package org.lucas.example.foundation.thread.demo.future;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.core.task.ExampleThreadExecutor;
import org.lucas.example.foundation.thread.demo.future.impl.CalculateService;
import org.lucas.example.foundation.thread.demo.future.impl.ExceptionService;

import java.util.concurrent.FutureTask;

/**
 * @create: 2017-11-13
 * @description:
 */
public class FutureDemo {

    @Test
    public void testFuture() throws Exception {
        /**
         * FutureTask对象继承Runnable和Future
         *
         * Callable对象自带返回结果
         * Runnable对象没有返回结果,需要指定特定的返回结果
         */
        FutureTask<Integer> futureTask = new FutureTask<>(new CalculateService(100));
        ExampleThreadExecutor.submit(futureTask);

        while (true) {
            if (futureTask.isDone()) {
                System.out.println(futureTask.get());
                break;
            }
        }
        ExampleThreadExecutor.destroy();
    }

    @Test
    public void exceptionFutureTest() throws Exception {
        FutureTask<Boolean> futureTask = new FutureTask<>(new ExceptionService());
        ExampleThreadExecutor.submit(futureTask);
        try {
            futureTask.get();
            Assertions.fail();
        } catch (final Exception e) {
            Assertions.assertEquals("java.lang.RuntimeException: 任务异常！", e.getMessage());
        }
        ExampleThreadExecutor.destroy();
    }
}
