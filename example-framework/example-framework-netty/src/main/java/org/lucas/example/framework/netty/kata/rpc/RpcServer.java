package org.lucas.example.framework.netty.kata.rpc;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 该例子：帧格式的第一部分为消息体，也就是业务需要传递的内容；
 * 第二部分为“:”号；第三部分为请求id，这里使用“：”把消息体与请求id分开，
 * 以便服务端提取这两部分内容，需要注意消息体内不能含有“：”号；第四部分“|”标识一个协议帧的结束，
 * 使用 Netty 的 DelimiterBasedFrameDecoder 来解决半包
 */
public class RpcServer {

    private int port;

    public RpcServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new RpcServer(port).run();
    }

    public void run() throws Exception {
        // 1 配置两级线程池，"boss"接收到连接，把连接信息注册到"worker"上
        // 1.1 "boss"事件循环器接收进来的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 1.2 "worker"事件循环器用来处理已经被接收的连接
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        // 2 创建业务处理 handler
        RpcServerHandler serverHandler = new RpcServerHandler();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    // 设置 Channel 类型(异步的服务器端 TCP Socket 连接)
                    .channel(NioServerSocketChannel.class)
                    //
                    // option 针对 boss 线程组
                    //
                    // 同一时间只能处理一个客户端连接，多个客户端来的时候，
                    // 服务端将不能处理的客户端连接请求放在队列中等待处理，
                    // backlog参数指定了队列长度(128)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // handler 针对 boss 线程组
                    .handler(new LoggingHandler(LogLevel.INFO))
                    // childOption 针对 worker 线程组
                    // 这个选项用于可能长时间没有数据交流的, 连接会测试链接的状态.
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // childHandler 针对 worker 线程组
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
                            ChannelPipeline p = ch.pipeline();
                            // 1 设置帧分隔符“|”解码器
                            ByteBuf delimiter = Unpooled.copiedBuffer("|".getBytes());
                            p.addLast(new DelimiterBasedFrameDecoder(1000, delimiter));
                            // 2 设置消息自动转换为 String 的解码器到管线。
                            p.addLast(new StringDecoder());
                            // 3 设置字符串消息自动进行编码的编码器到管线
                            p.addLast(new StringEncoder());
                            // 4 添加业务到管线
                            p.addLast(serverHandler);
                        }
                    });


            // 绑定端口，开始接收进来的连接
            ChannelFuture f = b.bind(port).sync();

            // 等待服务器  socket 关闭 。
            // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
            f.channel().closeFuture().sync();
        } finally {
            // 关闭两级线程池，释放线程
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
