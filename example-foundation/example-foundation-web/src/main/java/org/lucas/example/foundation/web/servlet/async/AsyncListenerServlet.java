package org.lucas.example.foundation.web.servlet.async;

import org.lucas.example.foundation.web.servlet.async.biz.BizExecutor;
import org.lucas.example.foundation.web.servlet.async.listener.MyAsyncListener;

import javax.servlet.AsyncContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@WebServlet(urlPatterns = "/testAsyncListener", asyncSupported = true)
public class AsyncListenerServlet extends HttpServlet {

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

        // 2 添加时间监听器
        asyncContext.addListener(new MyAsyncListener());

        // 3 提交异步任务并立即返回
        asyncContext.start(new BizExecutor(asyncContext));
    }

}
