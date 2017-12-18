package org.shaw.io.socket.transport.impl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * @create: 2017-12-15
 * @description:
 */
public class NioClient {

    private String hostname;

    private int port;

    public void Sendfile() {
        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            InetSocketAddress listener = new InetSocketAddress(hostname, port);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socketChannel != null) {
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
