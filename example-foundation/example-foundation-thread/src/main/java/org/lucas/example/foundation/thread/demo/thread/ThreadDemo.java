package org.lucas.example.foundation.thread.demo.thread;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.core.task.ExampleThreadExecutor;
import org.lucas.example.foundation.thread.demo.thread.support.MyThread;
import org.lucas.example.foundation.thread.demo.thread.support.Stage;

/**
 * 重排序：
 * 1.编译器优化的重排序
 * 2.指令级并行重排序（处理器优化）
 * 3.内存系统的重排序（处理器优化）
 * <p>
 * 单线程遵循as-if-serial,多线程并不一定遵循;
 */
public class ThreadDemo {

    @Test
    public void demoThread() {
        Thread stageThread = new Stage();
        ExampleThreadExecutor.submit(stageThread);
        ExampleThreadExecutor.destroy();
    }


    @Test
    public void demoOnSpinWait() {
        new Thread(() -> {
            int b = 1000000;
            long begin = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + " 开始执行");
            // 让出CPU资源，使用Thread.onSpinWait();比Thread.sleep(0);性能要好。
            while (b-- > 0) {
                Thread.onSpinWait();
            }
            System.out.println(Thread.currentThread().getName() + " 执行完毕 " + (System.currentTimeMillis() - begin));
        }, "b").start();
    }

    /**
     * 线程状态测试
     *
     * @throws Exception
     */
    @Test
    public void threadState() throws Exception {
        MyThread myThread = new MyThread("my");
        System.out.println("1.线程名称：" + myThread.getName() + "，线程状态:" + myThread.getState());
        myThread.start();
        myThread.join();
        System.out.println("2.线程名称：" + myThread.getName() + "，线程状态:" + myThread.getState());
    }
}
