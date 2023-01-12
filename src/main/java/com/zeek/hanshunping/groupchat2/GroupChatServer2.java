package com.zeek.hanshunping.groupchat2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author liweibo04 <liweibo04@58.com>
 * Created on 2023-01-12
 */
public class GroupChatServer2 {

    // 监听端口
    private int port;

    public GroupChatServer2(int port) {
        this.port = port;
    }

    // 编写 run 方法处理客户端请求
    public void run() throws InterruptedException {

        // 创建两个线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 获取到 pipeline
                            ChannelPipeline pipeline = ch.pipeline();
                            // 向 pipeline 添加解码器
                            pipeline.addLast("decoder", new StringDecoder());
                            // 向 pipeline 添加编码器
                            pipeline.addLast("encoder", new StringEncoder());
                            // 向 pipeline 加入业务处理 handler
                            pipeline.addLast(new GroupChatServerHandler2());
                        }
                    });

            System.out.println("netty 服务器启动------->");
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            // 监听关闭
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        GroupChatServer2 groupChatServer2 = new GroupChatServer2(7000);
        groupChatServer2.run();
    }
}
