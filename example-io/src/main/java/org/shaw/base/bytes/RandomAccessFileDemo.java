package org.shaw.base.bytes;

import org.shaw.util.IOUtils;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * @create: 2017-11-07
 * @description:
 */
public class RandomAccessFileDemo {
    public static void main(String[] args) throws Exception {
        IOTest.copyFile("/home/joy/桌面/15-反向代理实现nginx+apache动静分离.wmv", "/home/joy/桌面/dddddddd.wmv");
    }

    /**
     * int类型写入
     *
     * @param file
     * @param i
     * @throws Exception
     */
    public static long intWrite(File file, int i) throws Exception {
        if (!file.exists()) {
            throw new IllegalArgumentException("当前文件不存在！");
        }
        if (!file.isFile()) {
            throw new IllegalArgumentException(file + "不是文件！");
        }
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        // 序列化

        // 右移24位，&0xff 屏蔽高24位;
        randomAccessFile.write((i >>> 24) & 0xFF);
        // 右移16位，&0xff 屏蔽高24位;
        randomAccessFile.write((i >>> 16) & 0xFF);
        // 右移8位，&0xff 屏蔽高24位;
        randomAccessFile.write((i >>> 8) & 0xFF);
        // 右移0位，&0xff 屏蔽高24位;
        randomAccessFile.write((i >>> 0) & 0xFF);
        // randomAccessFile.writeInt(i); 直接写入
        long pointer = randomAccessFile.getFilePointer();
        randomAccessFile.close();
        return pointer;
    }

    /**
     * 写入一个字符
     *
     * @param file
     * @param c
     * @param encode
     * @return
     * @throws Exception
     */
    public static long charWrite(File file, Character c, String encode) throws Exception {
        if (!file.exists()) {
            throw new IllegalArgumentException("当前文件不存在！");
        }
        if (!file.isFile()) {
            throw new IllegalArgumentException(file + "不是文件！");
        }
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        byte[] bytes = IOUtils.getChartoBytes(c, encode);
        randomAccessFile.write(bytes);
        long pointer = randomAccessFile.getFilePointer();
        randomAccessFile.close();
        return pointer;
    }

    /**
     * 读取文件
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static byte[] readFile(File file) throws Exception {
        if (!file.exists()) {
            throw new IllegalArgumentException("当前文件不存在！");
        }
        if (!file.isFile()) {
            throw new IllegalArgumentException(file + "不是文件！");
        }
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        //读文件，必须把指针移到头部
        randomAccessFile.seek(0);
        byte[] bytes = new byte[(int) (randomAccessFile.length())];
        randomAccessFile.read(bytes);
        String str = new String(bytes, "utf-8");
        System.out.println(str);
        randomAccessFile.close();
        return bytes;
    }
}
