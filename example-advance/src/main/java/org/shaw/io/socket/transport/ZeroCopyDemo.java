package org.shaw.io.socket.transport;

import org.shaw.io.socket.transport.impl.TransferToClient;
import org.shaw.io.socket.transport.impl.TransferToServer;
import org.shaw.transport.ListenerServer;

import java.io.IOException;

/**
 * zero copy 直接通过kernel把disk的data传输给socket，提高性能
 */
public class ZeroCopyDemo {
    public static void main(String[] args) throws IOException {
        String srcFile = "C:\\Users\\john\\Desktop\\新建文本文档.txt";
        zeroCopyTest(srcFile);
    }

    /**
     * zeroCopy测试
     *
     * @param srcFile 传输文件
     */
    public static void zeroCopyTest(String srcFile) {
        TransferToServer dns = new TransferToServer();
        dns.mySetup();
        TransferToClient sfc = new TransferToClient(srcFile);
        sfc.testSendfile();
        dns.readData();
    }
}
