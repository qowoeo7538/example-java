package org.lucas.example.framework.web.spring.controller.async;

import org.lucas.component.thread.task.ThreadPoolTaskExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * 1）Tomcat容器接收路径为 personDeferredResult 的请求后，
 * 会分配一个容器线程来执行 DispatcherServlet 进行请求分派，
 * 请求被分到含有 personDeferredResult 路径的 controller，
 * 然后执行 listPostDeferredResult 方法，该方法内创建了一
 * 个 DeferredResult 对象，然后把处理任务提交到了线程池进行
 * 处理，最后返回 DeferredResult 对象。
 * <p>
 * 2）SpringMVC 内部在 listPersonDeferredResult 方法返回
 * 后会保存 DeferredResult 对象到内存队列或者列表，然后会调
 * 用 request.startAsync() 开启异步处理，并且调用 DeferredResult
 * 对象的 setResultHandler 方法，设置当异步结果产生后对结果
 * 进行重新路由的回调函数（逻辑在 WebAsyncManager 的 startDeferredResultProcessing
 * 方法），接着释放分配给当前请求的容器线程，与此同时当前请求
 * 的 DispatcherServlet 和所有 filters 也执行完毕了，但是
 * response 流还是保持打开（因为任务执行结果还没写回）。
 * <p>
 * 3）最终在业务线程池中执行的异步任务会产生一个结果，该结果会被
 * 设置到 DeferredResult 对象，然后设置的回调函数会被调用，接
 * 着 SpringMVC 会分派请求结果回到 Servlet 容器继续完成处理，
 * DispatcherServlet 被再次调用，使用返回的异步结果继续进行处
 * 理，最终把响应结果写回请求方。
 */
@RestController
@RequestMapping("/async")
public class AsyncController {

    @Autowired
    private ThreadPoolTaskExecutor executor;

    @GetMapping("/personDeferredResult")
    public DeferredResult<String> listPersonDeferredResult() {
        DeferredResult<String> deferredResult = new DeferredResult<>();
        executor.execute(() -> {
            try {
                // 执行异步处理
                Thread.sleep(3000);
                // 设置结果
                deferredResult.setResult("ok");
            } catch (Exception e) {
                e.printStackTrace();
                deferredResult.setErrorResult("error");
            }
        });
        return deferredResult;
    }

}
