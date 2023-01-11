package com.zeek.hanshunping.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.NettyRuntime;

/**
 * @ClassName NettyServer
 * @Description
 * @Author liweibo
 * @Date 2021/4/8 11:59 下午
 * @Version v1.0
 **/
public class NettyServer {

    public static void main(String[] args) throws InterruptedException {

        // cpu核数 目前mac本机上为12核
        System.out.println(NettyRuntime.availableProcessors());

        // 创建BossGroup 和 WorkerGroup
        // 说明
        // 1. 创建两个线程组 bossGroup 和 workerGroup
        // 2. bossGroup 只处理连接请求, 真正的和客户端业务处理, 会交给workerGroup完成
        // 3. 两个都是无限循环
        // 4. bossGroup 和 workerGroup 含有的子线程(NioEventLoop)的个数默认为 cpu核数*2
        EventLoopGroup boosGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 创建服务器端的启动对象, 配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();

            // 使用链式编程来进行设置
            bootstrap.group(boosGroup, workerGroup) // 设置两个线程组
                    .channel(NioServerSocketChannel.class) //使用NioServerSocketChannel 作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128) // 设置线程队列得到连接个数??
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // 设置保持活动连接状态??
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        // 给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 场景:
                            // 例如在推送系统的业务线程里面，根据用户的标识，找到对应的 Channel 引用，然后调用 Write 类方法向该用户推送消息，
                            // 就会进入到这种场景。最终的 Write 会提交到任务队列中后被异步消费
                            // 解决思路:
                            // 可以使用一个集合管理SocketChannel, 在推送消息时, 可以将业务加入到各个channel对应的NIOEventLoop的taskQueue或者scheduleTaskQueue中
                            System.out.println("k客户socketchannel hashcode=" + ch.hashCode());

                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new NettyServerHandler());
                        }
                    }); // 给我们的workerGroup 的 EventLoop 对应的管道设置处理器

            System.out.println("...服务器 is ready...");

            // 绑定一个端口并且同步, 生成了一个ChannelFuture 对象
            // 启动服务器并绑定端口
            ChannelFuture cf = bootstrap.bind(6668).sync();

            // 对关闭通道进行监听
            cf.channel().closeFuture().sync();
        }finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
