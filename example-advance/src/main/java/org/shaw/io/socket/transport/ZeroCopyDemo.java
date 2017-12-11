package org.shaw.io.socket.transport;

import org.shaw.io.socket.transport.impl.TransferToClient;
import org.shaw.io.socket.transport.impl.TransferToServer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * zero copy
 */
public class ZeroCopyDemo {
    public static void main(String[] args) throws IOException {
        // zeroCopyTest("C:\\Users\\john\\Desktop\\SQL测试.plain");
        String from = "src/main/java/zerocopy/1.data";
        String to = "src/main/java/zerocopy/2.data";
//		transferToDemo(from,to);
        transferFromDemo(from, to);
    }

    public static void transferFromDemo(String from, String to) throws IOException {
        FileChannel fromChannel = new FileInputStream(from).getChannel();
        FileChannel toChannel = new FileOutputStream(to).getChannel();

        long position = 0;
        long count = fromChannel.size();

        toChannel.transferFrom(fromChannel, position, count);

        fromChannel.close();
        toChannel.close();
    }

    public static void transferToDemo(String from, String to) {
        RandomAccessFile fromFile = null;
        RandomAccessFile toFile = null;
        try {
            fromFile = new RandomAccessFile(from, "rw");
            FileChannel fromChannel = fromFile.getChannel();

            toFile = new RandomAccessFile(to, "rw");
            FileChannel toChannel = toFile.getChannel();

            long position = 0;
            long count = fromChannel.size();
            fromChannel.transferTo(position, count, toChannel);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fromFile != null) {
                try {
                    fromFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (toFile != null) {
                try {
                    toFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

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
