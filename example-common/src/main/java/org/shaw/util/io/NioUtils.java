package org.shaw.util.io;

import org.shaw.util.io.support.ReadProcess;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ScatteringByteChannel;

/**
 * @create: 2017-12-12
 * @description: Nio工具类
 */
public class NioUtils {

    /**
     * 通道处理工具类
     *
     * @param channel     通道
     * @param readProcess 处理对象
     * @throws IOException
     */
    public static void channelRead(ScatteringByteChannel channel, ReadProcess readProcess) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        while (channel.read(buffer) > 0) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                readProcess.onProcess(buffer);
            }
            buffer.clear();
        }
    }

    /**
     * @param buffer
     * @param readProcess
     */
    public static void bufferProcess(ByteBuffer buffer, ReadProcess readProcess) {

    }
}
