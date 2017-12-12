package org.shaw.util.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ScatteringByteChannel;

/**
 * @create: 2017-12-12
 * @description:
 */
public class ChannelUtils {

    public static void channelRead(ScatteringByteChannel channel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        while (channel.read(buffer) != -1) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.print((char) buffer.get());
            }
            buffer.clear();
        }
    }
}
