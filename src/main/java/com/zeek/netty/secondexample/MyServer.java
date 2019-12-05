package com.zeek.netty.secondexample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @ClassName MyServer
 * @Description
 * @Date 2019/9/20 7:55 PM
 * @Version v1.0
 **/
public class MyServer {

    public static void main(String[] args) throws Exception {

        /**
         * 只是接受连接；死循环；
         * 主要是做一些初始化的工作，根据传递给构造函数的数值创建相应数量的EventExecutor；若没有传递值的话，在默认的情况为CPU数量（若操作系统支持CPU超频的话，CPU数量再乘以2）的2倍
         * 而EventExecutor的作用是：
         *  The {@link EventExecutor} is a special {@link EventExecutorGroup} which comes
         *  with some handy methods to see if a {@link Thread} is executed in a event loop.
         *  Besides this, it also extends the {@link EventExecutorGroup} to allow for a generic
         *  way to access methods.
         *  翻译过来就是: EventExecutor是一种特殊的EventExecutorGroup，它提供了一些可以查看一个Thread是否被一个事件循环执行的方便方法。除此之外，它还从EventExecutorGroup中继承了一些访问方法
         */

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 处理真正的请求；死循环
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(workerGroup, bossGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new MyServerInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();

        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}
