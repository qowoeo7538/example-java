package org.lucas.example.foundation.thread.demo.thread;

import org.lucas.example.foundation.thread.demo.thread.impl.Stage;

/**
 * 重排序：
 * 1.编译器优化的重排序
 * 2.指令级并行重排序（处理器优化）
 * 3.内存系统的重排序（处理器优化）
 * <p>
 * 单线程遵循as-if-serial,多线程并不一定遵循;
 */
public class ThreadDemo {
    public static void main(String[] args) {
        Thread stageThread = new Stage();
        new Thread(stageThread::run).start();
    }
}
