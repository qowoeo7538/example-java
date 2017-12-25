package org.shaw.transport.impl;

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
        SocketChannel sc = null;
        FileChannel fc = null;
        try {
            SocketAddress sad = new InetSocketAddress(host, port);
            sc = SocketChannel.open();
            // 关联这个通道的socket。
            sc.connect(sad);
            // 将连接改变为阻塞模型,该方法会将通道上锁
            sc.configureBlocking(true);
            fc = new FileInputStream(srcFile).getChannel();
            long start = System.nanoTime();
            long curnset = 0;
            curnset = fc.transferTo(0, fc.size(), sc);
            System.out.println("发送的总字节数:" + curnset
                    + " 耗时(ns):"
                    + (System.nanoTime() - start));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sc != null) {
                try {
                    sc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fc != null) {
                try {
                    fc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
