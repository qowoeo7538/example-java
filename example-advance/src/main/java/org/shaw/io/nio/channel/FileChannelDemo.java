package org.shaw.io.nio.channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileChannel 从文件中读写数据。
 */
public class FileChannelDemo {
    public static void main(String[] args) {
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile("C:\\Users\\john\\Desktop\\SQL测试.plain", "rw");
            // 获取流通道
            FileChannel inChannel = randomAccessFile.getChannel();
            // 创建一个容量为48字节的缓冲区.
            ByteBuffer buf = ByteBuffer.allocate(48);
            int bytesRead;
            // 通道中的数据读到缓冲区中
            while ((bytesRead = inChannel.read(buf)) != -1) {
                System.out.println("Read " + bytesRead);
                // 将缓存区position会被置为0,从头开始读取
                buf.flip();
                // 当前位置至结束是否还有其它元素
                while (buf.hasRemaining()) {
                    // 读取缓存区的数据
                    System.out.print((char) buf.get());
                }
                // 清理缓冲区
                buf.clear();
            }
            randomAccessFile.close();
        } catch (IOException ex) {
            ex.getStackTrace();
        } finally {
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
