package com.zeek.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

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
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        // 如果传递的是字符串形式的"123456L"将不会进入到编码器 MyLongToByteEncoder，只进入到 MyStringToByteEncoder
        ctx.writeAndFlush(123456L);

        // 如果传递的是字符串形式的123456L将不会进入到编码器 MyStringToByteEncoder，只进入到 MyLongToByteEncoder
        ctx.writeAndFlush("123456L");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
