package org.lucas.example.framework.spring.thread;

/**
 * 实际上是 Quartz 的 SimpleThreadPool 的子类，它监听 Spring 的生命周期回调。
 * 当你有一个可能需要 Quartz 和非 Quartz 组件共享的线程池时，通常会使用该实现。
 */
public class SimpleThreadPoolTaskExecutorDemo {
}
