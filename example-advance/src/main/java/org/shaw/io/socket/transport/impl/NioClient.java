package org.shaw.io.socket.transport.impl;

import org.shaw.util.io.NioUtils;

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
        FileInputStream fileInputStream = null;
        try {
            final SocketChannel socketChannel = SocketChannel.open();
            fileInputStream = new FileInputStream(srcFile);
            FileChannel fileChannel = fileInputStream.getChannel();

            InetSocketAddress listener = new InetSocketAddress(hostname, port);
            socketChannel.connect(listener);
            while (true) {
                NioUtils.channelRead(fileChannel, (buffer) -> {
                    try {
                        socketChannel.write(buffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                NioUtils.channelRead(socketChannel, (buffer) -> {
                    System.out.print((char) buffer.get());
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
