package org.lucas.example.foundation.web.servlet.async;

import org.lucas.example.foundation.web.servlet.async.listener.MyReadListener;
import org.lucas.example.foundation.web.servlet.async.listener.MyWriteListener;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/testAsyncReadBody", asyncSupported = true)
public class AsyncReadWriteBodyServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final AsyncContext asyncContext = req.startAsync();
        final ServletInputStream inputStream = req.getInputStream();
        // 设置数据读取就绪监听器
        inputStream.setReadListener(new MyReadListener(asyncContext));

        ServletOutputStream outputStream = resp.getOutputStream();
        // 设置数据写就绪监听器
        outputStream.setWriteListener(new MyWriteListener());
    }

}
