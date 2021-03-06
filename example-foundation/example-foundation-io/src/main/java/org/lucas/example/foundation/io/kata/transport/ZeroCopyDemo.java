package org.lucas.example.foundation.io.kata.transport;

import org.lucas.example.foundation.io.kata.transport.support.TransferToClient;
import org.lucas.example.foundation.io.kata.transport.support.TransferToServer;

import java.io.IOException;

/**
 * zero copy 直接通过kernel把disk的data传输给socket，提高性能
 */
public class ZeroCopyDemo {
    public static void main(String[] args) throws IOException {
        String srcFile = "C:\\Users\\john\\Desktop\\to.txt";
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
