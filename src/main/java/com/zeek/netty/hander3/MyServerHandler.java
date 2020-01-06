package com.zeek.netty.hander3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * @ClassName MyServerHandler
 * @Description
 * @Author liweibo
 * @Date 2020/1/6 4:24 PM
 * @Version v1.0
 **/
public class MyServerHandler extends SimpleChannelInboundHandler<PersonProtocol> {

    int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PersonProtocol msg) throws Exception {
        int length = msg.getLength();
        byte[] content = msg.getContent();

        System.out.println("服务器端接收到的数据: ");
        System.out.println("长度: " + length);
        System.out.println("内容: " + new String(content, Charset.forName("utf-8")));
        System.out.println("服务器端收到的消息数量: " + (++count));

        String responseMessage = UUID.randomUUID().toString();
        int responseLength = responseMessage.getBytes("UTF-8").length;
        byte[] responseContent = responseMessage.getBytes("UTF-8");

        PersonProtocol personProtocol = new PersonProtocol();
        personProtocol.setLength(responseLength);
        personProtocol.setContent(responseContent);

        ctx.writeAndFlush(personProtocol);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
