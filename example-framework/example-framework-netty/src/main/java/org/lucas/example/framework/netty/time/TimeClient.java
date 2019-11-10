package org.lucas.example.framework.netty.time;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @create: 2018-02-01
 * @description:
 */
public class TimeClient {

    private final static String HOST = "localhost";

    private final static int PORT = 8080;

    public static void main(String[] args) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // BootStrap 针对客户端或者无连接传输模式的 channel
            Bootstrap b = new Bootstrap();
            /**
             * 只指定一个 EventLoopGroup，那他就会既作为一个 boss group ，也会作为一个 worker group
             *
             * 客户端不需要 worker group
             */
            b.group(workerGroup);
            // 在客户端 channel 被创建时使用
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new TimeClientHandler());
                }
            });
            // 启动客户端
            ChannelFuture f = b.connect(HOST, PORT).sync();

            // 等待连接关闭
            f.channel().closeFuture().sync();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
