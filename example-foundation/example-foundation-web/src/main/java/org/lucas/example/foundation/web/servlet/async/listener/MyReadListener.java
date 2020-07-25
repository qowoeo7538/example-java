package org.lucas.example.foundation.web.servlet.async.listener;

import javax.servlet.AsyncContext;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyReadListener implements ReadListener {

    /**
     * 自定义线程池
     */
    private final static int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    private final static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(AVAILABLE_PROCESSORS,
            AVAILABLE_PROCESSORS * 2, 1,
            TimeUnit.MINUTES, new LinkedBlockingQueue<>(5), new ThreadPoolExecutor.CallerRunsPolicy());


    private AsyncContext asyncContext;

    public MyReadListener(AsyncContext asyncContext) {
        this.asyncContext = asyncContext;
    }

    /**
     * 当数据就绪时，通知容器线程进行数据读取
     *
     * @throws IOException
     */
    @Override
    public void onDataAvailable() throws IOException {
        try {
            // 读取请求体
            final ServletInputStream inputStream = asyncContext.getRequest().getInputStream();
            try {
                byte buffer[] = new byte[1 * 1024];
                int readBytes = 0;
                // 通过 inputStream.isReady() 发现数据已经准备就绪
                while (inputStream.isReady() && !inputStream.isFinished()) {
                    readBytes += inputStream.read(buffer);
                }
                System.out.println(Thread.currentThread().getName() + " Read: " + readBytes);
            } catch (IOException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        } finally {
        }
    }

    /**
     * 当请求体的数据全部读取完毕后，通知由容器线程进行业务处理
     * <p>
     * 此处可以自定义线程处理，释放容器线程
     */
    @Override
    public void onAllDataRead() throws IOException {
        // 1 提交 异步 任务
        POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // 2 ... 执行业务逻辑
                    TimeUnit.SECONDS.sleep(3);

                    // 3 获取响应对象设置响应结果
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
                    // 4 异步完成通知，标识任务完成
                    asyncContext.complete();
                }
            }
        });
    }

    @Override
    public void onError(Throwable t) {
        System.out.println("onError:" + t.getLocalizedMessage());
    }

}
