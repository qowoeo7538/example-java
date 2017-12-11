package org.shaw.io.nio.channel;

import org.shaw.io.nio.channel.impl.FileChannelImpl;

/**
 * FileChannel 从文件中读写数据。
 */
public class FileChannelDemo {
    public static void main(String[] args) {
        // 将通道数据写入缓存
        // FileChannelImpl.channeToBuffer("C:\\Users\\john\\Desktop\\SQL测试.plain");

        // 通道数据交换
        FileChannelImpl.channelTransferFrom("C:\\Users\\john\\Desktop\\新建文本文档.txt","C:\\Users\\john\\Desktop\\SQL测试.plain");
    }
}
