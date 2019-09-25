package com.zeek.netty.forthexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName MyServerInitializer
 * @Description
 * @Date 2019/9/25 7:42 PM
 * @Version v1.0
 **/
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new IdleStateHandler(10, 10000, 10000, TimeUnit.SECONDS));
        pipeline.addLast(new MyServerHandler());
    }
}
