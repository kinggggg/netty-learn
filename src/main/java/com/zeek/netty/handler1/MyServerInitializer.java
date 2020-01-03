package com.zeek.netty.handler1;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @ClassName MyServerInitializer
 * @Description
 * @Date 2019/9/25 10:49 AM
 * @Version v1.0
 **/
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new ServerMyByteToLongDecoder());
        pipeline.addLast(new ServerMyLongToByteEncoder());
        pipeline.addLast(new MyServerHandler());
    }
}
