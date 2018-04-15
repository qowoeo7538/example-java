package org.shaw.base.channel.impl;

import org.shaw.util.StreamHelper;

import java.io.IOException;
import java.io.RandomAccessFile;
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
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "rw")) {
            // 获取流通道
            FileChannel inChannel = randomAccessFile.getChannel();
            // 通道中的数据读到缓冲区中
            StreamHelper.channelRead(inChannel, (buffer) -> {
                System.out.print((char) buffer.get());
            });
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    /**
     * 通道间的数据交换
     *
     * @param fromPath 源文件
     * @param toPath   目标文件
     */
    public static void channelTransferFrom(String fromPath, String toPath) throws IOException {
        try (RandomAccessFile fromFile = new RandomAccessFile(fromPath, "rw");
             RandomAccessFile toFile = new RandomAccessFile(toPath, "rw")) {

            FileChannel fromChannel = fromFile.getChannel();
            FileChannel toChannel = toFile.getChannel();

            long position = 0;
            long count = fromChannel.size();
            // 通道数据交换
            toChannel.transferFrom(fromChannel, position, count);
            // 通道处理
            StreamHelper.channelRead(toChannel, (buffer) -> {
                System.out.print((char) buffer.get());
            });
        }
    }

    /**
     * 通道间的数据交换
     *
     * @param fromPath 源文件
     * @param toPath   目标文件
     */
    public static void channelTransferTo(String fromPath, String toPath) throws IOException {
        try (RandomAccessFile fromFile = new RandomAccessFile(fromPath, "rw");
             RandomAccessFile toFile = new RandomAccessFile(toPath, "rw")) {

            FileChannel fromChannel = fromFile.getChannel();
            FileChannel toChannel = toFile.getChannel();

            long position = 0;
            long count = fromChannel.size();
            fromChannel.transferTo(position, count, toChannel);
            // 通道处理
            StreamHelper.channelRead(fromChannel, (buffer) -> {
                System.out.print((char) buffer.get());
            });
        }
    }
}
