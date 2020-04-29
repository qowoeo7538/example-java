package org.lucas.example.framework.spring.thread;

/**
 * 这种 TaskExecutor 接口的实现不会异步地执行提交的任务，而是会同步使用调用线程来执行，
 * 这种实现主要用于没有必要多线程进行处理的情况，比如在进行简单的单元测试时。
 */
public class SyncTaskExecutorDemo {
}
