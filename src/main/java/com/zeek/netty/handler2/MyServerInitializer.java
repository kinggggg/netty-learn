package com.zeek.netty.handler2;


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
        // 客户端MyClient运行多次的话，下面的输出的内容均是一样，表明：多个客户端连接在Server端使用的是同一个MyServerInitializer
        System.out.println(this);

        ChannelPipeline pipeline = ch.pipeline();
        // 客户端MyClient运行多次的话，下面的输出的内容均不一样，表明：一个Channel，即，一个客户端的连接对应一个ChannelPipeline对象
        System.out.println(pipeline.getClass().getName() + "@" + Integer.toHexString(pipeline.hashCode()));


        pipeline.addLast(new MyServerHandler());
    }
}
