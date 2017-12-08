package org.shaw.io.nio.channel;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileChannel 从文件中读写数据。
 */
public class FileChannelDemo {
    public static void main(String[] args) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("C:\\Users\\john\\Desktop\\SQL测试.plain", "rw");
            // 获取流通道
            FileChannel inChannel = randomAccessFile.getChannel();
            // 字节缓冲区
            ByteBuffer buf = ByteBuffer.allocate(48);
        } catch (FileNotFoundException ex) {
            ex.getStackTrace();
        }
    }
}
