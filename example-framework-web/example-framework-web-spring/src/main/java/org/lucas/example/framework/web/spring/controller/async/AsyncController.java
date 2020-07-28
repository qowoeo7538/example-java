package org.lucas.example.framework.web.spring.controller.async;

import org.lucas.component.thread.task.ThreadPoolTaskExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;

import java.util.concurrent.Callable;

/**
 *
 */
@RestController
@RequestMapping("/async")
public class AsyncController {

    @Autowired
    private ThreadPoolTaskExecutor executor;

    /**
     * 1）Tomcat容器接收路径为 /async/deferredResult 的请求后，
     * 会分配一个容器线程来执行 DispatcherServlet 进行请求分派，
     * 请求被分到含有 /async/deferredResult 路径的 controller，
     * 然后执行 deferredResult 方法，该方法内创建了一个 DeferredResult
     * 对象，然后把处理任务提交到了线程池进行处理，最后返回 DeferredResult
     * 对象。
     * <p>
     * 2）SpringMVC 内部在 deferredResult 方法返回后会保存 DeferredResult
     * 对象到内存队列或者列表，然后会调用 request.startAsync()
     * 开启异步处理，并且调用 DeferredResult 对象的 setResultHandler
     * 方法，设置当异步结果产生后对结果进行重新路由的回调函数（逻
     * 辑在 WebAsyncManager 的 startDeferredResultProcessing
     * 方法），接着释放分配给当前请求的容器线程，与此同时当前请求的 DispatcherServlet
     * 和所有 filters 也执行完毕了，但是 response 流还是保持打开（因为任务执行结果还没写回）。
     * <p>
     * 3）最终在业务线程池中执行的异步任务会产生一个结果，该结果会被
     * 设置到 DeferredResult 对象，然后设置的回调函数会被调用，接
     * 着 SpringMVC 会分派请求结果回到 Servlet 容器继续完成处理，
     * DispatcherServlet 被再次调用，使用返回的异步结果继续进行处
     * 理，最终把响应结果写回请求方。
     */
    @GetMapping("/deferredResult")
    public DeferredResult<String> deferredResult() {
        System.out.println("---- begin deferredResult----");
        DeferredResult<String> deferredResult = new DeferredResult<>();
        executor.execute(() -> {
            try {
                // 执行异步处理
                Thread.sleep(3000);
                // 设置结果
                deferredResult.setResult("deferredResult ok.");
                System.out.println("---- end deferredResult----");
            } catch (Exception e) {
                e.printStackTrace();
                deferredResult.setErrorResult("deferredResult error.");
            }
        });
        return deferredResult;
    }

    /**
     * 返回一个异步任务后就直接返回了，其中的异步任务会使
     * 用 Spring 框架内部的 TaskExecutor 线程池来执行，
     * 其整个执行流程如下：
     * <p>
     * 1）Tomcat 容器接收路径为 /async/callableResult 的
     * 请求后，会分配一个容器线程来执行 DispatcherServlet 进
     * 行请求分派，接着请求被分到含有 /async/callableResult 路
     * 径的 controller，然后执行 callableResult 方法，返回一
     * 个 Callable 对象。
     * <p>
     * 2）SpringMVC 内部在 callableResult 方法返回后，调用 request.startAsync()
     * 开启异步处理，然后提交 Callable 任务到内部线程池TaskExecutor（非
     * 容器线程）中进行异步执行（WebAsyncManager 的 startCallableProcessing 方法内），接
     * 着释放分配给当前请求的容器线程，与此同时当前请求的 DispatcherServlet 和
     * 所有 filters 也执行完毕了，但是 response 流还是保持打开（因为Callable任务执行结果还没写回）。
     * <p>
     * 3）最终在线程池 TaskExecutor 中执行的异步任务会产生一个结
     * 果，然后 SpringMVC 会分派请求结果回到 Servlet 容器继续完成
     * 处理，DispatcherServlet 被再次调用，使用返回的异步结果继续
     * 进行处理，最终把响应结果写回请求方。
     * <p>
     * 注意：这种方式下异步执行默认使用内部的 SimpleAsyncTaskExecutor，其对每个请
     * 求都会开启一个线程，并没有很好地复用线程,
     *
     * @see org.lucas.example.framework.web.spring.config.WebMvcConfig#configureAsyncSupport(AsyncSupportConfigurer)}
     */
    @PostMapping("/callableResult")
    public Callable<String> callableResult() {
        System.out.println("---- begin callableResult----");
        return () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("---- end callableResult----");
            return "callableResult ok..";
        };
    }
}
