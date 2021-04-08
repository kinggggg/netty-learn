package com.zeek.hanshunping.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @ClassName NettyServerHandler
 * @Description
 * @Author liweibo
 * @Date 2021/4/9 12:17 上午
 * @Version v1.0
 *
 * 说明
 * 1. 我们自动以一个Handler, 需要继承Netty规定好的某个HandlerAdapter
 * 2. 这时我们定义的一个Handler才能称之为handler
 **/
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    // 读取数据 这里我们可以读取客户端发送的信息
    // 1. ChannelHandlerContext ctx: 上下文对象, 含有 管道pipeline, 通道channel, 地址
    // 2. Object msg: 就是客户端发送的数据 默认Object
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx = " + ctx);
        // 将 msg 转换成 ByteBuf
        // ByteBuf 是 Netty 提供的, 不是NIO的 ByteBuffer
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送消息是:" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址:" + ctx.channel().remoteAddress());
    }

    // 数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // writeAndFlush = write + flush
        // 将数据写入缓存, 并刷新
        // 一般讲, 我们对发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端~喵", CharsetUtil.UTF_8));
    }

    // 处理异常, 一般是需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
