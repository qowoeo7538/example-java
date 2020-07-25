package org.lucas.example.foundation.web.servlet.async.biz;

import javax.servlet.AsyncContext;
import javax.servlet.ServletInputStream;
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
            // 3.1 读取请求体(阻塞)
            // 请求体中并非一开始就有数据，所以当我们的业务线程池 POOL_EXECUTOR 中的线
            // 程调用 inputStream.read 方法时是会被阻塞的，等内核接收到请求方发来的数据后，
            // 该方法才会返回，而这之前 POOL_EXECUTOR 中的线程会一直被阻塞，
            // 这就是我们所说的阻塞IO。阻塞IO会消耗宝贵的线程。
            final ServletInputStream inputStream = asyncContext.getRequest().getInputStream();
            byte buffer[] = new byte[1 * 1024];
            int readBytes;
            int total = 0;
            while ((readBytes = inputStream.read(buffer)) > 0) {
                total += readBytes;
            }

            // 3.2 ... 执行业务逻辑
            System.out.println(total);
            TimeUnit.SECONDS.sleep(3);

            // 3.3 获取响应对象设置响应结果
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
            // 3.4 异步完成通知，标识任务完成
            asyncContext.complete();
        }
    }

}
