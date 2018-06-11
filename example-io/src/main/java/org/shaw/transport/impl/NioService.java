package org.shaw.transport.impl;

import org.shaw.util.StreamUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @create: 2017-12-15
 * @description: NIO服务端
 */
public class NioService {

    private String hostname;

    private int port;

    public NioService(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    /**
     * 测试服务
     */
    public void mySetup() {
        try (ServerSocketChannel ssc = ServerSocketChannel.open()) {
            int index = 0;
            ssc.socket().bind(new InetSocketAddress(hostname, port));
            ssc.configureBlocking(false);
            Selector selector = Selector.open();
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            ByteBuffer readBuff = ByteBuffer.allocate(128);
            ByteBuffer returnData = ByteBuffer.allocate(128);

            // writeBuff.flip(); // make buffer ready for reading
            while (true) {
                int readyChannels = selector.select();
                if (readyChannels == 0) {
                    continue;
                }
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    it.remove();
                    if (key.isAcceptable()) {
                        SocketChannel socketChannel = ssc.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        // readBuff.clear();
                        // socketChannel.read(readBuff);
                        // readBuff.flip();
                        // System.out.println(new String(readBuff.array()));
                        StreamUtils.channelRead(socketChannel, (buffer) -> {
                            System.out.print((char) buffer.get());
                        });
                        key.interestOps(SelectionKey.OP_WRITE);
                    } else if (key.isWritable()) {
                        returnData.rewind();
                        returnData.put("\nreceived\n".getBytes());
                        returnData.flip();
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        socketChannel.write(returnData);
                        key.interestOps(SelectionKey.OP_READ);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
