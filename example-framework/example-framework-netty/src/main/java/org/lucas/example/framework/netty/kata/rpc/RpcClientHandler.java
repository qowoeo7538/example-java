package org.lucas.example.framework.netty.kata.rpc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.lucas.example.framework.netty.util.FutureMapUtil;

import java.util.concurrent.CompletableFuture;

public class RpcClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 响应结果处理
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 提交给线程池异步执行，释放IO线程
        AllChannelHandler.channelRead(() -> {
            // 1 根据请求id，获取对应future
            CompletableFuture future = FutureMapUtil.remove(((String) msg).split(":")[1]);
            // 2 如果存在，则设置 future 响应结果
            if (null != future) {
                // 从协议帧内获取服务端写回的数据，并调用 future的complete方法把结
                // 果设置到future，这时候由于调用 future 的 get() 方法而被阻塞的线程就返回结果了。
                future.complete(((String) msg).split(":")[0]);
            }
        });
    }
}
