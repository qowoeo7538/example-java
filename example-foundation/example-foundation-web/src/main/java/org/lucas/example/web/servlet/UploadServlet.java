package org.lucas.example.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

/**
 * Created by joy on 17-3-27.
 */

@WebServlet(urlPatterns = "/uploadServlet")
public class UploadServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /* 从request中获取文件字节长度
        int length = req.getContentLength();*/

        /* 获取头信息
        Enumeration<String> headerNames = req.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String element = headerNames.nextElement();
            System.out.println(element+": "+req.getHeader(element));
        }*/


        // 保存临时文件
        InputStream fileSource = req.getInputStream();
        String tempFile = "/home/joy/桌面/tempFile";
        FileOutputStream tempFileOutputStream = new FileOutputStream(tempFile);

        byte[] bytes = new byte[10 * 1024];
        int i = 0;
        while ((i = fileSource.read(bytes)) != -1) {
            System.out.println(new String(bytes, 0, i));

            tempFileOutputStream.write(bytes, 0, i);
            tempFileOutputStream.flush();
            // System.out.print("已读字节：" + i + ", 剩余字节: " + fileSource.available());
        }
        RandomAccessFile randomAccessFile = new RandomAccessFile(tempFile, "r");
        /* ====================== 获取文件的开始位置 ====================== */
        StringBuilder sb = new StringBuilder();
        String str = null;
        i = 0;
        int y = 0;
        while (i < 1) {
            str = randomAccessFile.readLine();
            /*if (y == 0) {
                sb.append('\n');
                y++;
                continue;
            }*/
            sb.append(str + '\n');
            if (str.length() <= 0) {
                i++;
            }

        }
        int startPosition = sb.length() + 1;
        // int startPosition = (int) randomAccessFile.getFilePointer(); 字节起始位置

        /*==================== 文件切割 ====================*/
        String boundary = req.getHeader("content-type");
        String[] strings = boundary.split("=");
        sb.delete(0, sb.length());
        boundary = strings[strings.length - 1];

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(tempFile)));
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line + "\n"); // 读取时并没读取换行
        }
        System.out.println("===========================================");
        System.out.println(sb.toString());

        String[] strSbu = sb.toString().split(boundary);
        String savePaht = "/home/joy/桌面";
        for (int j = 1; j < strSbu.length - 1; j++) {
            // System.out.println("============================ 开始 ============================");
            // 获取文件名
            int fileNameStrat = strSbu[j].indexOf("filename=\"") + 10;
            int fileNameEnd = strSbu[j].indexOf("\"", fileNameStrat);
            String fileName = strSbu[j].substring(fileNameStrat, fileNameEnd);

            // 设置保存路径
            //System.out.println("=======================设置保存路径========================");
            // String realPath = req.getServletContext().getRealPath(File.separator) + "images";
            // String resourcePath = this.getClass().getClassLoader().getResource("../../").getPath()+"images";  同上效果

            if (!new File(savePaht).exists()) throw new IllegalArgumentException("目录不存在！");

            if (fileName.trim().length() > 0) {
                File saveFile = new File(savePaht, "save_" + fileName);
                FileOutputStream fileOutputStream = new FileOutputStream(saveFile);

                //获取文件内容
                String fileStr = strSbu[j].substring(startPosition, strSbu[j].lastIndexOf("-") - 2);
                if (fileStr.trim().length() > 0) {
                    fileOutputStream.write(fileStr.getBytes());
                }
                fileOutputStream.close();
            }
            // System.out.println("============================ 结束 ============================");
        }
        /*=========================================*/


        tempFileOutputStream.close();
        fileSource.close();


        // System.out.println("=======================获取上传文件名========================");
        randomAccessFile.seek(0);
        randomAccessFile.readLine();
        str = randomAccessFile.readLine();
        String[] strs = str.split("\"");
        String fileName = new String(strs[strs.length - 1].getBytes("iso8859-1"), "utf-8");  // 解码  编码
        if (fileName.lastIndexOf("\\") != -1) {
            int beginIndex = fileName.lastIndexOf("\\") + 1;
            fileName = fileName.substring(beginIndex);
        }
        // System.out.println("文件名： " + fileName);

        // 获取文件的末尾位置
        Long endPosition = randomAccessFile.length();
        i = 0;
        while (endPosition > 0 && i < 2) { //末尾有一个换行
            endPosition--;
            randomAccessFile.seek(endPosition);
            if (randomAccessFile.readByte() == '\n') {
                i++;
            }
        }
        endPosition -= 1; // 删掉换行符。


        // 获取上传文件内容
        // System.out.println("=======================保存上传文件内容========================");
        // randomAccessFile.seek(startPosition);
        /*FileOutputStream saveFileOutputStream = new FileOutputStream(saveFile);
        bytes = new byte[1024];
        while (randomAccessFile.getFilePointer() < endPosition) {
            randomAccessFile.read(bytes, 0, bytes.length);
            saveFileOutputStream.write(bytes);
            // System.out.println(new String(bytes, "utf-8"));
            long writeSize = endPosition - randomAccessFile.getFilePointer();
            if (writeSize < bytes.length) {
                bytes = new byte[(int) writeSize];
            }
        }*/
        // new File(tempFile).delete();
        randomAccessFile.close();
        // saveFileOutputStream.close();
        req.setAttribute("info", "上传成功");
        req.getRequestDispatcher("jsp/test/udLoad/udLoad.jsp").forward(req, resp); // getRequestDispatcherDispatcher: 该servlet处理完毕，继续分发到下一个资源处理。
    }
}
