package org.lucas.example.framework.netty.kata.rpc;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.reactivex.Flowable;
import io.reactivex.processors.ReplayProcessor;
import io.reactivex.schedulers.Schedulers;
import org.lucas.example.framework.netty.util.FutureMapUtil;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

public class RpcClient {

    /**
     * 连接通道
     * <p>
     * 注册业务的 NettyClientHandler 处理器到链接 channel 的管线里
     * 面，并且在与服务端完成 TCP 三次握手后把对应的 channel 对象保存
     * 了下来。
     */
    private volatile Channel channel;

    /**
     * 请求id生成器
     */
    private static final AtomicLong INVOKE_ID = new AtomicLong(0);

    /**
     * 启动器
     */
    private Bootstrap b;

    public RpcClient() {
        // 1 配置客户端
        EventLoopGroup group = new NioEventLoopGroup();
        RpcClientHandler handler = new RpcClientHandler();
        try {
            b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    // 关闭 Nagle 算法，Nagle 算法对减少需要传输的包来优化网络。
                    // 对传输数据较少的包，关闭 Nagle 算法可以减少延迟。
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            // 1.1 设置帧分隔符解码器
                            ByteBuf delimiter = Unpooled.copiedBuffer("|".getBytes());
                            p.addLast(new DelimiterBasedFrameDecoder(1000, delimiter));
                            // 1.2 设置消息内容自动转换为 String 的解码器到管线
                            p.addLast(new StringDecoder());
                            // 1.3 设置字符串消息自动进行编码的编码器到管线
                            p.addLast(new StringEncoder());
                            // 1.4 添加业务 Handler 到管线
                            p.addLast(handler);
                        }
                    });
            // 发起连接请求，并同步等待连接完成。
            ChannelFuture f = b.connect("127.0.0.1", 12800).sync();
            if (f.isDone() && f.isSuccess()) {
                this.channel = f.channel();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据消息内容和请求 id，拼接消息帧
     */
    private String generatorFrame(String msg, String reqId) {
        return msg + ":" + reqId + "|";
    }

    /**
     * rpc 异步调用
     */
    public CompletableFuture<String> rpcAsyncCall(String msg) {
        // 1 创建future
        CompletableFuture<String> future = new CompletableFuture<>();
        // 2 创建消息 id
        String reqId = INVOKE_ID.getAndIncrement() + "";
        // 3 根据消息，请求 id 创建协议帧
        msg = generatorFrame(msg, reqId);
        // 4 nio 异步发起网络请求，马上返回
        try {
            this.sendMsg(msg);
        } catch (Exception e) {
            future.cancel(true);
            throw e;
        }
        // 5 保存 future 对象
        FutureMapUtil.put(reqId, future);
        return future;
    }

    /**
     * rpc 同步调用
     */
    public String rpcSyncCall(String msg) throws InterruptedException, ExecutionException {
        // 1 创建future
        CompletableFuture<String> future = new CompletableFuture<>();
        // 2 创建 消息 id
        String reqId = INVOKE_ID.getAndIncrement() + "";
        // 3 消息 体 后 追加 消息 id 和 帧 分隔符
        msg = generatorFrame(msg, reqId);
        // 4 nio 异步 发起 网络 请求， 马上 返回
        try {
            this.sendMsg(msg);
        } catch (Exception e) {
            future.cancel(true);
            throw e;
        }
        // 5 保存 future
        FutureMapUtil.put(reqId, future);
        // 6 同步等待结果
        return future.get();
    }

    /**
     * 异步转反应式
     */
    public Flowable<String> rpcAsyncCallFlowable(String msg) {
        return Flowable.fromFuture(rpcAsyncCall(msg)).subscribeOn(Schedulers.io());
    }

    /**
     * 同步转反应式
     */
    public Flowable<String> rpcSyncCallFlowable(String msg) {
        return Flowable.fromCallable(() -> rpcSyncCall(msg)).subscribeOn(Schedulers.io());
    }

    /**
     * 异步转反应式
     */
    public Flowable<String> rpcAsyncCallFlowable3(String msg) {
        final ReplayProcessor<String> flowable = ReplayProcessor.createWithSize(1);
        CompletableFuture<String> future = rpcAsyncCall(msg);
        future.whenComplete((v, t) -> {
            if (t != null) {
                flowable.onError(t);
            } else {
                flowable.onNext(v);
                flowable.onComplete();
            }
        });
        return flowable;
    }

    /**
     * 异步转反应式
     */
    public Flowable<String> rpcAsyncCallFlowable4(String msg) {
        // 1.1 使用defer操作，当订阅时候在执行rpc调用
        return Flowable.defer(() -> {
            // 1.2 创建含有一个元素的流
            final ReplayProcessor<String> flowable = ReplayProcessor.createWithSize(1);
            // 1.3 具体执行RPC调用
            CompletableFuture<String> future = rpcAsyncCall(msg);
            // 1.4 等rpc结果返回后设置结果到流对象
            future.whenComplete((v, t) -> {
                if (t != null) {
                    // 1.4.1 结果异常则发射错误信息
                    flowable.onError(t);
                } else {
                    // 1.4.2 结果OK，则发射出rpc返回结果
                    flowable.onNext(v);
                    // 1.4.3 结束流
                    flowable.onComplete();
                }
            });
            return flowable;
        });
    }

    /**
     * 异步转反应式,订阅时候切换到其他线程
     */
    public Flowable<String> rpcAsyncCallFlowable5(String msg) {
        return Flowable.defer(() -> {
            final ReplayProcessor<String> flowable = ReplayProcessor.createWithSize(1);
            CompletableFuture<String> future = rpcAsyncCall(msg);
            future.whenComplete((v, t) -> {
                if (t != null) {
                    flowable.onError(t);
                } else {
                    flowable.onNext(v);
                    flowable.onComplete();
                }
            });
            return flowable;
        }).observeOn(Schedulers.io());
    }

    public void close() {
        if (null != b) {
            b.config().group().shutdownGracefully();
        }
        if (null != channel) {
            channel.close();
        }
    }

    /**
     * 该方法是非阻塞的，会马上返回，所以不会阻塞业务线程
     */
    private void sendMsg(String msg) {
        channel.writeAndFlush(msg);
    }
}
