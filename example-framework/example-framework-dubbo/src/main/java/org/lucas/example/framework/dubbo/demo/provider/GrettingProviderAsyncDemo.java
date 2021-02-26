package org.lucas.example.framework.dubbo.demo.provider;

import org.apache.dubbo.common.utils.NamedThreadFactory;
import org.apache.dubbo.rpc.AsyncContext;
import org.apache.dubbo.rpc.RpcContext;
import org.lucas.example.framework.dubbo.demo.common.service.GrettingServiceRpcContext;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 在 Provider 端非异步执行时，其对调用方发来的请求的处理是在 Dubbo 内部线程
 * 模型的线程池中的线程来执行的，在 Dubbo 中服务提供方提供的所有服务接口都使用
 * 这一个线程池来执行，所以当一个服务执行比较耗时时，可能会占用线程池中的很多线
 * 程，这可能就会影响到其他服务的处理。
 * <p>
 * Provider端异步执行则将服务的处理逻辑从 Dubbo 内部线程池切换到业务自定义线
 * 程，避免 Dubbo 线程池中线程被过度占用，有助于避免不同服务间的互相影响。但是
 * 需要注意，Provider端异步执行对节省资源和提升RPC响应性能是没有效果的，另外副
 * 作用会增加一次上下文切换（从Dubbo内部线程池线程切换到业务线程）。
 */
public class GrettingProviderAsyncDemo implements GrettingServiceRpcContext {

    /**
     * 创建业务自定义线程池
     */
    private final ThreadPoolExecutor bizThreadPool = new ThreadPoolExecutor(8, 16, 1, TimeUnit.MINUTES,
            new SynchronousQueue<>(), new NamedThreadFactory("biz-thread-pool"),
            new ThreadPoolExecutor.CallerRunsPolicy());

    @Override
    public String sayHello(String name) {
        // 1 开启异步，返回一个asyncContext。
        final AsyncContext asyncContext = RpcContext.startAsync();
        // 2 提交到业务线程池处理，同时也释放了 Dubbo 内部线程池中的线程
        bizThreadPool.execute(() -> {
            // 2.1 切换任务的上下文,将RpcContext从 Dubbo 线程切换到用户创建的新线程。
            // 因为 RpcContext.getContext() 是 ThreadLocal 变量，不能跨线程，这里
            // 切换上下文就是为了把保存的上下文内容设置到当前线程内，这样当前线程就可以获取了。
            asyncContext.signalContextSwitch();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 2.2 将结果写入异步上下文
            asyncContext.write("Hello " + name + " " + RpcContext.getContext().getAttachment("company"));
        });
        // 3 服务处理任务提交到业务线程池后直接返回 null。
        return null;
    }
}
