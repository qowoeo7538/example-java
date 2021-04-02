package org.lucas.example.foundation.io.kata.transport.support;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 服务端
 */
public class TransferToServer {

    private ServerSocketChannel listener;

    private int port = 9026;

    public TransferToServer() {
    }

    public TransferToServer(int port) {
        this.port = port;
    }

    /**
     * 监听端口
     */
    public void mySetup() {
        InetSocketAddress listenAddr = new InetSocketAddress(port);
        try {
            listener = ServerSocketChannel.open();
            ServerSocket ss = listener.socket();
            // 允许重复使用该地址（Time_wait时间范围内允许重复连接操作）
            ss.setReuseAddress(true);
            ss.bind(listenAddr);
            System.out.println("监听的端口:" + listenAddr.toString());
        } catch (IOException e) {
            System.out.println("端口绑定失败 : "
                    + listenAddr.toString() + " 端口可能已经被使用,出错原因: "
                    + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * 获取所监听端口的数据
     */
    public void readData() {
        ByteBuffer dst = ByteBuffer.allocate(4096);
        try {
            while (true) {
                SocketChannel conn = listener.accept();
                System.out.println("创建的连接: " + conn);
                // 将连接改变为阻塞模型
                conn.configureBlocking(true);
                for (int nread = 0; nread != -1; ) {
                    try {
                        // 将连接中的数据读取到缓冲区
                        nread = conn.read(dst);
                    } catch (IOException e) {
                        e.printStackTrace();
                        nread = -1;
                    }
                    dst.flip();
                    while (dst.hasRemaining()) {
                        // 读取缓存区的数据
                        System.out.print((char) dst.get());
                    }
                    dst.clear();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
