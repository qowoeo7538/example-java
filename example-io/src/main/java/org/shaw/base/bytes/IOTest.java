package org.shaw.base.bytes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by joy on 17-2-5.
 */
public class IOTest {

    private final static String SRC_FILE = "/home/joy/桌面/in.sh";

    private final static String COPY_FILE = "/home/joy/桌面/in.sh";

    public static void main(String[] args) throws IOException {
        copyFile(SRC_FILE, COPY_FILE);
    }

    /**
     * 文件拷贝(最佳拷贝方式)
     *
     * @param srcFile
     * @param copyFile
     * @throws IOException
     */
    public static void copyFile(String srcFile, String copyFile) throws IOException {
        // 默认为删除文件创建一个新文件，true为追加内容
        FileOutputStream fileOutputStream = new FileOutputStream(copyFile);
        FileInputStream fileInputStream = new FileInputStream(srcFile);
        byte[] buf = new byte[10 * 1024];
        int y;
        while ((y = fileInputStream.read(buf, 0, buf.length)) != -1) {
            fileOutputStream.write(buf, 0, y);
            fileOutputStream.flush();
            if (fileInputStream.available() < buf.length && fileInputStream.available() > 0) {
                buf = new byte[fileInputStream.available()];
            }
        }
        fileInputStream.close();
        fileOutputStream.close();
    }

    /**
     * 读取文件字节
     *
     * @param fileName
     * @throws IOException
     */
    public static void printHex(String fileName) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        int b;
        int x = 1;
        while ((b = fileInputStream.read()) != -1) { // fileInputStream.read() 返回读取的一个字节
            if (b <= 0xf) {
                System.out.print(0);
            }
            System.out.print(Integer.toHexString(b) + " ");
            if (x++ % 10 == 0) {
                System.out.println();
            }
        }
        fileInputStream.close();
    }

    /**
     * 常用文件字节读取方式
     *
     * @param fileName
     * @throws IOException
     */
    public static void printHexByByteArray(String fileName) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        byte[] bytes = new byte[20 * 1024];
        int b;
        while ((b = fileInputStream.read(bytes, 0, bytes.length)) != -1) { // read(byte[] b, int off, int len) 返回读取字节的长度
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
        fileInputStream.close();
    }
}
