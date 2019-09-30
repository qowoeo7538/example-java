package org.lucas.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @create: 2018-02-01
 * @description:
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 在连接被建立并且准备进行通信时被调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /**
         * 通过 ChannelHandlerContext.alloc() 得到一个当前的ByteBufAllocator，分配一个新的缓冲
         *
         * {@link ByteBuf} 没有 flip() 方法, 因为有两个指针，一个对应读操作一个对应写操作。
         */
        final ByteBuf time = ctx.alloc().buffer(4);
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
        /**
         * ChannelFuture 代表了一个还没有发生的 I/O 操作, 因为在 Netty 里所有的操作都是异步的.
         * 可以通过在 {@code ChannelFuture} 对象添加一个 {@code ChannelFutureListener} 监听操作.
         */
        final ChannelFuture f = ctx.writeAndFlush(time);
        // 等价代码 f.addListener(ChannelFutureListener.CLOSE);
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                assert f == future;
                ctx.close();
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
