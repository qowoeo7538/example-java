package org.shaw.transport;

import org.shaw.transport.support.SelectorProcess;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @create: 2017-12-13
 * @description:
 */
public class ListenerServer {

    /** 端口 */
    private int port;

    /** host地址 */
    private String hostname;

    /** 监听操作类型 */
    private int operation;

    /** 反应器 */
    private Selector sel;

    private ServerSocketChannel serverSocketChannel;

    public void setup() {
        InetSocketAddress listenAddr = new InetSocketAddress(hostname, port);
        try {
            serverSocketChannel = ServerSocketChannel.open();
            ServerSocket ss = serverSocketChannel.socket();
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
                SocketChannel conn = serverSocketChannel.accept();
                conn.configureBlocking(false);
                conn.register(sel, operation);
                SelectorProcess selectorProcess = new SelectorProcess(sel);
                selectorProcess.readyProcess((channel) -> {
                    channel.read();
                });
                for (int nread = 0; nread != -1; ) {
                    try {
                        // 将连接中的数据读取到缓冲区
                        nread = conn.read(dst);
                    } catch (IOException e) {
                        e.printStackTrace();
                        nread = -1;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
