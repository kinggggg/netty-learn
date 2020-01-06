package com.zeek.netty.hander3;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @ClassName MyPersonEncoder
 * @Description
 * @Author liweibo
 * @Date 2020/1/6 4:22 PM
 * @Version v1.0
 **/
public class MyPersonEncoder extends MessageToByteEncoder<PersonProtocol> {

    @Override
    protected void encode(ChannelHandlerContext ctx, PersonProtocol msg, ByteBuf out) throws Exception {

        System.out.println("MyPersonEncoder invoked!");

        out.writeInt(msg.getLength());
        out.writeBytes(msg.getContent());
    }
}
