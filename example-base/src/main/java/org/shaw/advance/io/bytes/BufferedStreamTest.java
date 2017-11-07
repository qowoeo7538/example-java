package org.shaw.advance.io.bytes;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 或读取操作时，都会加上缓冲，这种流模式提高了IO的性能
 * 从应用程序中把输入放入文件，相当于将一缸水倒入到另一个缸中:
 * FileOutputStream--->write()方法相当于一滴一滴地把水“转移”过去
 * DataOutputStream-->writeXxx()方法会方便一些，相当于一瓢一瓢把水“转移”过去
 * BufferedOutputStream--->write方法更方便，相当于一飘一瓢先放入桶中，再从桶中倒入到另一个缸中，性能提高了
 * <p>
 * Created by joy on 17-2-7.
 */
public class BufferedStreamTest {
    public static void main(String[] args) throws Exception {
        copyFileByBuffer("/home/joy/桌面/in.sh", "/home/joy/桌面/wwwwwww.sh");
    }

    /**
     * 利用缓冲区拷贝文件
     */
    public static void copyFileByBuffer(String srcData, String copyData) throws Exception {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(srcData));
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(copyData));
        int bte;
        while ((bte = bufferedInputStream.read()) != -1) {
            bufferedOutputStream.write(bte);
            bufferedOutputStream.flush(); //刷新缓冲区
        }
        bufferedInputStream.close();
        bufferedOutputStream.close();
    }
}
