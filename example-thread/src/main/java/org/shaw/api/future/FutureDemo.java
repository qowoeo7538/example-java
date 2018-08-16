package org.shaw.api.future;

import org.junit.Test;
import org.shaw.core.task.ExampleThreadExecutor;
import org.shaw.api.future.impl.CalculateService;

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
}
