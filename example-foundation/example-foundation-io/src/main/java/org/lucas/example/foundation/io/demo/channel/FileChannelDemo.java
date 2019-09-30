package org.lucas.example.foundation.io.demo.channel;

import org.lucas.example.foundation.io.demo.channel.impl.FileChannelImpl;

/**
 * FileChannel 从文件中读写数据。
 * <p>
 * 1）不能修改为非阻塞模式
 */
public class FileChannelDemo {

    /** 源文件 */
    private final static String to = "C:\\Users\\john\\Desktop\\to.txt";

    /** 目标文件 */
    private final static String from = "C:\\Users\\john\\Desktop\\from.txt";

    public static void main(String[] args) {
        // 将通道数据写入缓存
        FileChannelImpl.channeToBuffer(to);

        // 通道数据交换
        // A.TransferFrom(B) :: A <- B
        // FileChannelImpl.channelTransferFrom(from, to);
        // A.TransferTo(B) :: A -> B
        // FileChannelImpl.channelTransferTo(from, to);
    }
}
