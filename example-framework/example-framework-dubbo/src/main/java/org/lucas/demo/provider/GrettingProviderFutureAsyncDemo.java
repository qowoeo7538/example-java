package org.lucas.demo.provider;

import org.apache.dubbo.common.utils.NamedThreadFactory;
import org.apache.dubbo.rpc.RpcContext;
import org.lucas.common.service.GrettingServiceAsync;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 基于定义 CompletableFuture 签名的接口实现异步执行需要接口方法返回值
 * 为 CompletableFuture，并且方法内部使用 CompletableFuture.supplyAsync 让
 * 本来应由 Dubbo 内部线程池中线程处理的服务，转为由业务自定义线程池中线程来处理。
 * CompletableFuture.supplyAsync 方法会马上返回一个 CompletableFuture 对
 * 象（所以Dubbo内部线程池线程会得到及时释放），传递的业务函数则由业务线程
 * 池 bizThreadpool 执行。需要注意，调用 sayHello方法的线程是 Dubbo 线程模型线程池
 * 中的线程，而业务在 bizThreadpool 中的线程处理，所以代码 1保存了RpcContext对
 * 象（ThreadLocal变量），以便在业务处理线程中使用。
 */
public class GrettingProviderFutureAsyncDemo implements GrettingServiceAsync {

    /**
     * 创建业务自定义线程池
     */
    private final ThreadPoolExecutor bizThreadpool = new ThreadPoolExecutor(8, 16, 1, TimeUnit.MINUTES,
            new SynchronousQueue(), new NamedThreadFactory("biz-thread-pool"),
            new ThreadPoolExecutor.CallerRunsPolicy());

    @Override
    public CompletableFuture<String> sayHello(String name) {
        // 1 为supplyAsync提供自定义线程池bizThreadpool，避免使用JDK公用线程池(ForkJoinPool.commonPool())
        // 使用CompletableFuture.supplyAsync让服务处理异步化进行处理
        // 保存当前线程的上下文
        RpcContext context = RpcContext.getContext();

        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("async return ");
            return "Hello " + name + " " + context.getAttachment("company");
        }, bizThreadpool);
    }

}
