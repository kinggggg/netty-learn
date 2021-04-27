package com.zeek.hanshunping.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
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
        // 比如这里我们有一个非常耗时长的业务 -> 异步执行 -> 提交该channel 对应的
        // NIOEventLoop 的 taskQueue中


        // 解决方案1 用户程序自定义的普通任务

        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10 * 1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端~喵2", CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        System.out.println("go on ...");

//        System.out.println("服务器读取线程 " + Thread.currentThread().getName());
//        System.out.println("server ctx = " + ctx);
//
//        System.out.println("看看channel 和 pipeline的关系");
//        Channel channel = ctx.channel();
//        ChannelPipeline pipeline = channel.pipeline(); // 本质是一个双向链表, 出站入站
//
//        // 将 msg 转换成 ByteBuf
//        // ByteBuf 是 Netty 提供的, 不是NIO的 ByteBuffer
//        ByteBuf buf = (ByteBuf) msg;
//        System.out.println("客户端发送消息是:" + buf.toString(CharsetUtil.UTF_8));
//        System.out.println("客户端地址:" + channel.remoteAddress());
    }

    // 数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // writeAndFlush = write + flush
        // 将数据写入缓存, 并刷新
        // 一般讲, 我们对发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端~喵1", CharsetUtil.UTF_8));
    }

    // 处理异常, 一般是需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
