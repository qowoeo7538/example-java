package org.shaw.nio.io.socket.transport;

import org.shaw.io.socket.transport.impl.NioClient;
import org.shaw.io.socket.transport.impl.NioService;
import org.shaw.util.thread.DefaultThreadFactory;

/**
 * @create: 2017-12-15
 * @description: NIO测试
 */
public class NioDemo {

    /** 服务端host地址 */
    private final static String hostname = "127.0.0.1";

    /** 服务端端口 */
    private final static int port = 9092;

    /** 传输文件 */
    private final static String srcFile = "C:\\Users\\john\\Desktop\\coverage-error.log";

    public static void main(String[] args) {
        NioService nioService = new NioService(hostname, port);
        NioClient nioClient = new NioClient(hostname, port);
        DefaultThreadFactory.execute(() -> {
            nioService.mySetup();
        });


        DefaultThreadFactory.execute(() -> {
            nioClient.sendFile(srcFile);
        });

    }
}
