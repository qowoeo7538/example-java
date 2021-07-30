package org.lucas.example.foundation.thread.demo.future;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.core.task.ExampleThreadExecutor;
import org.lucas.example.foundation.thread.demo.future.support.CalculateService;
import org.lucas.example.foundation.thread.demo.future.support.ExceptionService;

import java.util.concurrent.FutureTask;

/**
 * @create: 2017-11-13
 * @description:
 */
class FutureDemo {

    @Test
    void demoFutureTask() throws Exception {
        /**
         * FutureTask对象继承Runnable和Future
         *
         * Callable对象自带返回结果
         * Runnable对象没有返回结果,需要指定特定的返回结果
         */
        //构造FutureTask，并且传入需要真正进行业务逻辑处理的类,该类一定是实现了Callable接口的类
        FutureTask<Integer> futureTask = new FutureTask<>(new CalculateService(100));

        //这里提交任务future,则开启线程执行futureTask的call()方法执行
        ExampleThreadExecutor.submit(futureTask);
        while (true) {
            // 判断任务是否完成
            if (futureTask.isDone()) {
                // 获取任务结果
                System.out.println(futureTask.get());
                break;
            }
        }
        ExampleThreadExecutor.destroy();
    }

    @Test
    void demoExceptionFuture() throws Exception {
        FutureTask<Boolean> futureTask = new FutureTask<>(new ExceptionService());
        ExampleThreadExecutor.submit(futureTask);
        try {
            futureTask.get();
        } catch (final Exception e) {
            System.out.println("回调发生异常");
            Assertions.assertEquals("java.lang.RuntimeException: 任务异常！", e.getMessage());
        }
        ExampleThreadExecutor.destroy();
    }
}
