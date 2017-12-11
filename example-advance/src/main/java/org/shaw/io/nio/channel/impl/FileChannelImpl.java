package org.shaw.io.nio.channel.impl;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @create: 2017-12-11
 * @description:
 */
public class FileChannelImpl {
    /**
     * 通道数据写入缓冲区
     *
     * @param filePath 文件路径
     */
    public static void channeToBuffer(String filePath) {
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(filePath, "rw");
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

    /**
     * 通道间的数据交换
     *
     * @param fromPath 目标文件
     * @param toPath   源文件
     */
    public static void channelTransferFrom(String fromPath, String toPath) {
        RandomAccessFile fromFile = null;
        RandomAccessFile toFile = null;
        try {
            fromFile = new RandomAccessFile(fromPath, "rw");
            FileChannel fromChannel = fromFile.getChannel();

            toFile = new RandomAccessFile(toPath, "rw");
            FileChannel toChannel = toFile.getChannel();

            long position = 0;
            long count = fromChannel.size();

            toChannel.transferFrom(fromChannel, position, count);

            // 读取通道数据
            ByteBuffer buf = ByteBuffer.allocate(1024);
            while ((fromChannel.read(buf)) != -1) {
                buf.flip();
                while (buf.hasRemaining()) {
                    System.out.print((char) buf.get());
                }
                buf.clear();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (fromFile != null) {
                try {
                    fromFile.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (toFile != null) {
                try {
                    toFile.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
