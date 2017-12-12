package org.shaw.io.nio.channel;

import org.shaw.io.nio.channel.impl.FileChannelImpl;

/**
 * FileChannel 从文件中读写数据。
 */
public class FileChannelDemo {
    public static void main(String[] args) {
        String from = "C:\\Users\\john\\Desktop\\from.txt";
        String to = "C:\\Users\\john\\Desktop\\to.txt";

        // 将通道数据写入缓存
        // FileChannelImpl.channeToBuffer(to);

        // 通道数据交换
        // A.TransferFrom(B) :: A <- B
        FileChannelImpl.channelTransferFrom(from, to);
        // A.TransferTo(B) :: A -> B
        // FileChannelImpl.channelTransferTo(from, to);
    }
}
