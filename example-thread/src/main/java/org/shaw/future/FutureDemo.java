package org.shaw.future;

import org.junit.Test;
import org.shaw.core.task.ExampleThreadExecutor;
import org.shaw.future.impl.RealService;

import java.util.concurrent.*;

/**
 * @create: 2017-11-13
 * @description:
 */
public class FutureDemo {

    @Test
    public void testFuture(){
        Long start = System.currentTimeMillis();
        /**
         * FutureTask对象继承Runnable和Future
         *
         * Callable对象自带返回结果
         * Runnable对象没有返回结果,需要指定特定的返回结果
         */
        FutureTask<String> futureTask = new FutureTask<>(new RealService("hello,world"));
        ExampleThreadExecutor.submit(futureTask);
        // 表示正在处理其他逻辑,或者业务
        try {
            Thread.sleep(1000);
            System.out.println("最后结果-->" + futureTask.get());
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
        }
        Long end = System.currentTimeMillis();
        Long useTime = end - start;
        System.out.println("程序运行了-->" + useTime + "毫秒");

        ExampleThreadExecutor.destroy();
    }
}
