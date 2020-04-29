package org.lucas.example.framework.spring.thread;

/**
 * 这种 TaskExecutor 接口的实现不会复用线程，对应每个请求会新创建一个对应的线程来执行。
 * 它支持的并发限制将阻止任何超出限制的调用，这个可以通过调用 setConcurrencyLimit 方法来限制并发数，
 * 默认是不限制并发数的。
 */
public class SimpleAsyncTaskExecutorDemo {

}
