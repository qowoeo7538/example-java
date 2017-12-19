package org.shaw.io.socket.transport.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @create: 2017-12-15
 * @description:
 */
public class NioClient {

    private String hostname;

    private int port;

    private String srcfile;

    public NioClient(String hostname, int port, String srcfile) {
        this.hostname = hostname;
        this.port = port;
        this.srcfile = srcfile;
    }

    /**
     * @see #fileToChannel(String, WritableByteChannel)
     */
    public void Sendfile() {
        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            InetSocketAddress listener = new InetSocketAddress(hostname, port);
            socketChannel.connect(listener);
            ByteBuffer writeBuffer = ByteBuffer.allocate(4096);
            ByteBuffer readBuffer = ByteBuffer.allocate(4096);
            while (true) {
                writeBuffer.rewind();
                socketChannel.write(writeBuffer);
                readBuffer.clear();
                socketChannel.read(readBuffer);
            }
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

    /**
     * 将目标文件输出到目标通道
     *
     * @param srcFile 目标文件
     * @param channel 目标通道
     */
    private void fileToChannel(String srcFile, WritableByteChannel channel) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(srcFile);
            FileChannel srcChannel = fileInputStream.getChannel();
            srcChannel.transferTo(0, srcChannel.size(), channel);
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
