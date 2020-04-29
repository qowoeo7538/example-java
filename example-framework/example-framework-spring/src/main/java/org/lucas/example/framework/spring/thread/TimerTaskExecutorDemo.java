package org.lucas.example.framework.spring.thread;

/**
 * 该实现使用单个java.util.Timer对象作为其内部异步线程来执行任务。它与
 * SyncTaskExecutor 的不同之处在于，该实现对所有提交的任务都在Timer内
 * 的单独线程中执行，尽管提交的多个任务的执行是顺序同步的。
 */
public class TimerTaskExecutorDemo {
}
