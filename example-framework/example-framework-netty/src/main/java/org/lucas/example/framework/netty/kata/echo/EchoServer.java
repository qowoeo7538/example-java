package org.lucas.example.framework.netty.kata.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @create: 2018-01-30
 * @description:
 */
public class EchoServer {

    private int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new EchoServer(port).run();
    }

    public void run() throws Exception {
        /**
         * "boss"事件循环器接收进来的连接
         * "worker"事件循环器用来处理已经被接收的连接
         * 一旦"boss"接收到连接，就会把连接信息注册到"worker"上
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    // 设置 Channel 类型(异步的服务器端 TCP Socket 连接)
                    .channel(NioServerSocketChannel.class)
                    // ChannelInitializer 配置一个新的 Channel
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        /**
                         * 通道初始化的时候调用
                         *
                         * @param ch         SocketChannel Socket通道
                         * @throws Exception
                         */
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            // 添加一个 DiscardServerHandler 处理类
                            ch.pipeline().addLast(new EchoServerHandler());
                        }
                    })
                    /**
                     * option 针对 boss 线程组
                     *
                     * 同一时间只能处理一个客户端连接，多个客户端来的时候，
                     * 服务端将不能处理的客户端连接请求放在队列中等待处理，
                     * backlog参数指定了队列长度(128)
                     */
                    .option(ChannelOption.SO_BACKLOG, 128)
                    /**
                     * childOption 针对 worker 线程组
                     *
                     * 这个选项用于可能长时间没有数据交流的, 连接会测试链接的状态.
                     */
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            // 绑定端口，开始接收进来的连接
            ChannelFuture f = b.bind(port).sync();

            // 等待服务器  socket 关闭 。
            // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
