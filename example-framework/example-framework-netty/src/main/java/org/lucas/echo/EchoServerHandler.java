package org.lucas.echo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * ECHO 对任何接收的数据都会返回一个响应。
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 每当从客户端收到新的数据时，这个方法会在收到消息时被调用
     *
     * @param ctx {@code ChannelHandlerContext} 对象能够触发 I/O 事件和操作.
     * @param msg netty 将读到的数据放入 msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 逐字地把接受到的消息写入到 msg
        ctx.write(msg);
        /**
         * ctx.write(Object) 方法不会使消息写入到通道上，他被缓冲在了内部，你
         * 需要调用 ctx.flush() 方法来把缓冲区中数据强行输出。或者你可以用更简
         * 洁的 cxt.writeAndFlush(msg) 以达到同样的目的。
         */
        ctx.flush();
    }

    /**
     * 事件处理方法出现 Throwable 对象该方法被调用
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 丢弃服务器处理
     *
     * @param msg 消息
     */
    private void Discard(Object msg) {
        ByteBuf in = (ByteBuf) msg;
        try {
            while (in.isReadable()) {
                System.out.print((char) in.readByte());
                System.out.flush();
            }
        } finally {
            // 丢弃收到的数据
            ReferenceCountUtil.release(msg);
        }
    }
}
