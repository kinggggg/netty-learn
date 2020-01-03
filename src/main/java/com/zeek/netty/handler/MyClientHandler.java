package com.zeek.netty.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.time.LocalDate;

/**
 * @ClassName MyProtobufClientHandler
 * @Description
 * @Date 2019/9/25 2:08 PM
 * @Version v1.0
 **/
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress());
        System.out.println("client output " + msg);
        ctx.writeAndFlush("helloworld");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        // 如果传递的是字符串形式的 123456L 将不会进入到编码器 MyStringToByteEncoder，只进入到 MyLongToByteEncoder
//        ctx.writeAndFlush(123456L);
//        ctx.writeAndFlush(1L);
//        ctx.writeAndFlush(2L);
//        ctx.writeAndFlush(3L);
//        ctx.writeAndFlush(4L);

        // 如果传递的是字符串形式的 "helloworld" 将不会进入到编码器 MyLongToByteEncoder，只进入到 MyStringToByteEncoder。
        // 如果在Pipeline中没有配置 MyStringToByteEncoder 时此消息不会发送到服务器端进行处理。
        // 如果在Pipeline中没有配置 MyStringToByteEncoder 的前提下，还希望该消息发送到服务器端的话，需要通过
        //
        ctx.writeAndFlush(Unpooled.copiedBuffer("helloworld", Charset.forName("utf-8")));
//        ctx.writeAndFlush("helloworld");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
