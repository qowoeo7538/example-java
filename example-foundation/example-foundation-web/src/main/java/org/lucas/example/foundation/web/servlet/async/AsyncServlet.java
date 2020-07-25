package org.lucas.example.foundation.web.servlet.async;

import org.lucas.example.foundation.web.servlet.async.biz.BizExecutor;

import javax.servlet.AsyncContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * asyncSupported：是否支持异步
 * <p>
 * ·请求被 Servlet 容器接收，然后从 Servlet 容器(例如Tomcat)中获取一个线程来执行，
 * 请求被流转到 Filter 链进行处理，然后查找具体的 Servlet 进行处理。
 * <p>
 * ·Servlet 内使用 “req.startAsync()；” 开启异步处理，返回异步处理上下文 AsyncContext 对象，然
 * 后开启异步线程（可以是 Tomcat 容器中的其他线程，也可以是业务自己创建的线程）对请求进行具体处
 * 理（这可能会发起一个远程 rpc 调用或者一个数据库请求）；开启异步线程后，当前 Servlet 就返回
 * 了（分配给其执行的容器线程也就释放了），并且不对请求方产生响应结果。
 * <p>
 * ·异步线程对请求处理完毕后，会通过持有的AsyncContext对象把结果写回请求方.
 */
@WebServlet(urlPatterns = "/testAsync", asyncSupported = true)
public class AsyncServlet extends HttpServlet {

    /**
     * 自定义线程池
     */
    private final static int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    private final static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(AVAILABLE_PROCESSORS,
            AVAILABLE_PROCESSORS * 2, 1,
            TimeUnit.MINUTES, new LinkedBlockingQueue<>(5), new ThreadPoolExecutor.CallerRunsPolicy());

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) {
        // 1 开启异步servlet，获取异步上下文
        // AsyncContext 保存请求/响应相关的上下文信息
        final AsyncContext asyncContext = req.startAsync();

        // 2 提交异步任务并立即返回
        // 提交的任务将交由容器中的其它线程完成，其调用线程（容器线程）将被释放
        // asyncContext.start(new Executor(asyncContext));
        // 通过自定义业务线程执行提交业务，释放容器线程
        POOL_EXECUTOR.execute(new BizExecutor(asyncContext));
    }
}
