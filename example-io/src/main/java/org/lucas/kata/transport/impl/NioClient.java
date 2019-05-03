package org.lucas.kata.transport.impl;

import org.shaw.util.StreamUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @create: 2017-12-15
 * @description:
 */
public class NioClient {

    private String hostname;

    private int port;

    public NioClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void sendFile(String srcFile) {
        try (FileInputStream fileInputStream = new FileInputStream(srcFile)) {
            final SocketChannel socketChannel = SocketChannel.open();
            FileChannel fileChannel = fileInputStream.getChannel();

            InetSocketAddress listener = new InetSocketAddress(hostname, port);
            socketChannel.connect(listener);
            while (true) {
                StreamUtils.channelRead(fileChannel, (buffer) -> {
                    try {
                        socketChannel.write(buffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                StreamUtils.channelRead(socketChannel, (buffer) -> {
                    System.out.print((char) buffer.get());
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
