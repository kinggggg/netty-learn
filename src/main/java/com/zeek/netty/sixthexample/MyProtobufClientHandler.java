package com.zeek.netty.sixthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDate;

/**
 * @ClassName MyProtobufClientHandler
 * @Description
 * @Date 2019/9/25 2:08 PM
 * @Version v1.0
 **/
public class MyProtobufClientHandler extends SimpleChannelInboundHandler<DataInfo.Person> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataInfo.Person msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        DataInfo.Person person = DataInfo.Person.newBuilder()
                .setName("张三")
                .setAge(20)
                .setAddress("北京")
                .build();

        ctx.writeAndFlush(person);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


}
