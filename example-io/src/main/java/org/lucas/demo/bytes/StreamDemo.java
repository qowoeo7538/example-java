package org.lucas.demo.bytes;

import org.junit.Test;
import org.lucas.demo.bytes.impl.CopyFileKata;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by joy on 17-2-5.
 */
public class StreamDemo {

    private static final String SOURCE = "/home/joy/桌面/in.sh";

    private static final String TARGET = "/home/joy/桌面/in.sh";

    /**
     * 文件流传输
     */
    @Test
    public void fileStream() throws IOException {
        CopyFileKata.copyFile(SOURCE, TARGET);
    }


    /**
     * 读取文件字节(方式一)
     */
    @Test
    public void readStream1() throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(SOURCE)) {
            int b;
            int x = 1;
            // fileInputStream.read() 返回读取的一个字节
            while ((b = fileInputStream.read()) != -1) {
                if (b <= 0xf) {
                    System.out.print(0);
                }
                System.out.print(Integer.toHexString(b) + " ");
                if (x++ % 10 == 0) {
                    System.out.println();
                }
            }
        }
    }

    /**
     * 常用文件字节读取方式（方式二）
     */
    @Test
    public void readStream2() throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(SOURCE)) {
            byte[] bytes = new byte[20 * 1024];
            int b;
            // read(byte[] b, int off, int len) 返回读取字节的长度
            while ((b = fileInputStream.read(bytes, 0, bytes.length)) != -1) {
                for (int i = 0, x = 1; i < b; i++, x++) {
                    if (bytes[i] < 0xf) {
                        System.out.print(0);
                    }
                    System.out.print(Integer.toHexString(bytes[i] & 0xff) + " ");
                    if (x % 10 == 0) {
                        System.out.println();
                    }
                }
            }
        }
    }
}
