package org.lucas.example.foundation.thread.demo.future;

import org.junit.jupiter.api.Test;
import org.lucas.component.thread.task.ThreadPoolTaskExecutor;
import org.lucas.example.foundation.core.util.DataProducerHelper;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;

public class ExecutorCompletionServiceDemo {

    @Test
    public void demoExecutorCompletionService() throws InterruptedException, ExecutionException {
        CompletionService<String> service = new ExecutorCompletionService<>(new ThreadPoolTaskExecutor());
        for (int i = 0; i < 4; i++) {
            service.submit(() -> {
                Thread.sleep(DataProducerHelper.nextInt(100));
                System.out.println(Thread.currentThread().getName() + "|完成任务");
                return "data" + DataProducerHelper.nextInt(10);
            });
        }
        for (int j = 0; j < 4; j++) {
            // 这一行没有完成的任务就阻塞
            Future<String> take = service.take();
            // 这一行在这里不会阻塞，引入放入队列中的都是已经完成的任务
            String result = take.get();
            System.out.println("获取到结果：" + result);
        }
    }
}
