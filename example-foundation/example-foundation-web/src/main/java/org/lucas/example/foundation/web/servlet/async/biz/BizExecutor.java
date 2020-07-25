package org.lucas.example.foundation.web.servlet.async.biz;

import javax.servlet.AsyncContext;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class BizExecutor implements Runnable {

    private AsyncContext asyncContext;

    public BizExecutor(AsyncContext asyncContext) {
        this.asyncContext = asyncContext;
    }

    @Override
    public void run() {
        try {
            // 3.1 ... 执行业务逻辑
            TimeUnit.SECONDS.sleep(3);

            // 3.2 获取响应对象设置响应结果
            asyncContext.getResponse().setContentType("text/html");
            PrintWriter out = asyncContext.getResponse().getWriter();
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Hello World</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>welcome this is my servlet1!!!</h1>");
            out.println("</body>");
            out.println("</html>");
        } catch (final Exception e) {
            System.out.println(e.getLocalizedMessage());
        } finally {
            // 3.3 异步完成通知，标识任务完成
            asyncContext.complete();
        }
    }

}
