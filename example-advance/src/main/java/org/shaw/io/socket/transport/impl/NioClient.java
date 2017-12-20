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

    private String srcFile;

    public NioClient(String hostname, int port, String srcFile) {
        this.hostname = hostname;
        this.port = port;
        this.srcFile = srcFile;
    }

    public void Sendfile() {
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
                    System.out.println("服务端返回数据：" + (char) buffer.get() + ",剩余长度：" + buffer.remaining());
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
