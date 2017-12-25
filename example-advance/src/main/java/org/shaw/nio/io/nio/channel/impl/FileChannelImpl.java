package org.shaw.nio.io.nio.channel.impl;

import org.shaw.util.io.NioUtils;

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
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(filePath, "rw");
            // 获取流通道
            FileChannel inChannel = randomAccessFile.getChannel();
            // 通道中的数据读到缓冲区中
            NioUtils.channelRead(inChannel, (buffer) -> {
                System.out.print((char) buffer.get());
            });
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
     * @param fromPath 源文件
     * @param toPath   目标文件
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
            // 通道数据交换
            toChannel.transferFrom(fromChannel, position, count);
            // 通道处理
            NioUtils.channelRead(toChannel, (buffer) -> {
                System.out.print((char) buffer.get());
            });
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

    /**
     * 通道间的数据交换
     *
     * @param fromPath 源文件
     * @param toPath   目标文件
     */
    public static void channelTransferTo(String fromPath, String toPath) {
        RandomAccessFile fromFile = null;
        RandomAccessFile toFile = null;
        try {
            fromFile = new RandomAccessFile(fromPath, "rw");
            FileChannel fromChannel = fromFile.getChannel();

            toFile = new RandomAccessFile(toPath, "rw");
            FileChannel toChannel = toFile.getChannel();

            long position = 0;
            long count = fromChannel.size();
            fromChannel.transferTo(position, count, toChannel);
            // 通道处理
            NioUtils.channelRead(fromChannel, (buffer) -> {
                System.out.print((char) buffer.get());
            });
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fromFile != null) {
                try {
                    fromFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (toFile != null) {
                try {
                    toFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
