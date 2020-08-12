package org.lucas.example.framework.netty.kata.rpc;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * ECHO 对任何接收的数据都会返回一个响应。
 * <p>
 * Sharable 注解是让服务端所有接收的链接对应的 channel 复用同一个 EchoServerHandler
 * 实例，这里可以使用 @Sharable 方式是因为 NettyServerHandler 内的处理是无状态的，不会
 * 存在线程安全问题。
 */
@Sharable
public class RpcServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 每当从客户端收到新的数据时，这个方法会在收到消息时被调用
     *
     * @param ctx {@code ChannelHandlerContext} 对象能够触发 I/O 事件和操作.
     * @param msg netty 将读到的数据放入 msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 1 获取消息体，并解析出请求ID。
        // 这里 msg 已经是一个完整的文本协议。
        String str = (String) msg;
        String reqId = str.split(":")[1];

        // 2 拼接响应结果，请求 id, 协议帧分隔符(模拟服务端执行服务产生结果)
        String resp = generatorFrame("收到消息：", reqId);

        // 3 写回响应
        // ctx.write(Unpooled.copiedBuffer(resp.getBytes()))法不会使消息写入到通道上，他被缓冲在了内部，
        // 要调用 ctx.flush() 方法来把缓冲区中数据强行输出。或者你可以用更简洁的 cxt.writeAndFlush(msg) 以
        // 达到同样的目的。
        ctx.writeAndFlush(Unpooled.copiedBuffer(resp.getBytes()));
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
     * 根据 消息 内容 和 请求 id， 拼接 消息 帧
     */
    public String generatorFrame(String msg, String reqId) {
        return msg + ":" + reqId + "|";
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
