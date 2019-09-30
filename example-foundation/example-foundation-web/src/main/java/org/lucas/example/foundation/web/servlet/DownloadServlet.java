package org.lucas.example.foundation.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by joy on 17-4-1.
 */
@WebServlet(urlPatterns = "/downloadServlet")
public class DownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getParameter("fileName");
        String downloadPath = req.getServletContext().getRealPath(File.separator) + "images/test/" + fileName;
        System.out.println(downloadPath);
        File downloadFile = new File(downloadPath);
        if (!downloadFile.exists()) {
            req.setAttribute("downloadInfo", "该文件不存在！");
            req.getRequestDispatcher("jsp/test/udLoad/udLoad.jsp").forward(req, resp);
        }
        resp.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"");
        resp.setContentType("application/x-msdownload");

        FileInputStream fileInputStream = new FileInputStream(downloadFile);
        ServletOutputStream servletOutputStream = resp.getOutputStream();
        byte[] buf = new byte[10 * 1024];
        int i = 0;
        while ((i = fileInputStream.read(buf)) != -1) {
            servletOutputStream.write(buf, 0, i);
        }
        fileInputStream.close();
        servletOutputStream.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
