package com.zeek.hanshunping.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @ClassName NettyClient
 * @Description
 * @Author liweibo
 * @Date 2021/4/9 12:29 上午
 * @Version v1.0
 **/
public class NettyClient {

    public static void main(String[] args) throws InterruptedException {

        // 客户端需要一个事件循环组
        EventLoopGroup group = new NioEventLoopGroup();

        // 创建客户端启动对象
        // 注意客户端使用的是 Bootstrap, 而不是ServerBootstrap
        Bootstrap bootstrap = new Bootstrap();

        try {
            // 设置相关参数
            bootstrap.group(group) // 设置线程组
                    .channel(NioSocketChannel.class) // 设置客户端通道的实现类(反射用)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new NettyClientHandler()); // 加入自己的处理器
                        }
                    });

            System.out.println("客户端 ok...");

            // 启动客户端去连接服务器端
            // 关于 ChannelFuture 要分析, 涉及到Netty的异步模型
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();

            // 给关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }
}
