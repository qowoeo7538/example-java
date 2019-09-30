package org.lucas.example.io.kata.transport.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @create: 2017-12-11
 * @description:
 */
public class TransferToClient {

    private String host = "localhost";

    private int port = 9026;

    private String srcFile;

    public TransferToClient(String srcFile) {
        this.srcFile = srcFile;
    }

    public TransferToClient(String srcFile, String host, int port) {
        this.srcFile = srcFile;
        this.host = host;
        this.port = port;
    }

    public void testSendfile() {
        try (SocketChannel sc = SocketChannel.open();
             FileChannel fc = new FileInputStream(srcFile).getChannel()) {
            SocketAddress sad = new InetSocketAddress(host, port);
            // 关联这个通道的socket。
            sc.connect(sad);
            // 将连接改变为阻塞模型,该方法会将通道上锁
            sc.configureBlocking(true);

            long start = System.nanoTime();
            long curnset = 0;
            curnset = fc.transferTo(0, fc.size(), sc);
            System.out.println("发送的总字节数:" + curnset
                    + " 耗时(ns):"
                    + (System.nanoTime() - start));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
