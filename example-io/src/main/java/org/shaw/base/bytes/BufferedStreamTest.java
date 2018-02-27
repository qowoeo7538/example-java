package org.shaw.base.bytes;

import java.io.*;

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

    private final static String FILE_TO = "/home/joy/桌面/in.sh";

    private final static String FILE_FROM = "/home/joy/桌面/wwwwwww.sh";

    public static void main(String[] args) throws Exception {
        copyFileByBuffer(FILE_TO, FILE_FROM);
    }

    /**
     * 利用缓冲区拷贝文件
     */
    public static void copyFileByBuffer(String srcData, String copyData) throws IOException {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(srcData));
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(copyData))) {
            int bte;
            while ((bte = bufferedInputStream.read()) != -1) {
                bufferedOutputStream.write(bte);
                //刷新缓冲区
                bufferedOutputStream.flush();
            }
        }
    }
}
