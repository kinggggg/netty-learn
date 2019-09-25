package com.zeek.netty.firstexample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @ClassName TestServer
 * @Description 虽然程序比较简短，但是大多数的netty流程都是这样的一个流程
 * @Date 2019/9/17 12:34 PM
 * @Version v1.0
 **/
public class TestServer {

    public static void main(String[] args) throws InterruptedException {

        // 只接受连接，自己不亲自处理，将接受的连接请求交由workerGroup进行处理
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 真正处理连接
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    // TestServerInitializer当连接建立完成后，会自动调用TestServerInitializer中的initChannel方法
                    .childHandler(new TestServerInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
