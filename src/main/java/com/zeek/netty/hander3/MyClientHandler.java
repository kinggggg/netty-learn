package com.zeek.netty.hander3;

import com.zeek.netty.sixthexample.MyDataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

/**
 * @ClassName MyClientHandler
 * @Description
 * @Author liweibo
 * @Date 2020/1/6 4:30 PM
 * @Version v1.0
 **/
public class MyClientHandler extends SimpleChannelInboundHandler<PersonProtocol> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PersonProtocol msg) throws Exception {
        int length = msg.getLength();
        byte[] content = msg.getContent();

        System.out.println("客户端接收到的内容：");
        System.out.println("长度：" + length);
        System.out.println("内容: " + new String(content, Charset.forName("utf-8")));
        System.out.println("客户端接受到的消息数量: " + (++count));

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            String messageToBeSent = "sent from client ";
            byte[] content = messageToBeSent.getBytes("UTF-8");
            int length = content.length;

            PersonProtocol personProtocol = new PersonProtocol();
            personProtocol.setLength(length);
            personProtocol.setContent(content);

            ctx.writeAndFlush(personProtocol);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
