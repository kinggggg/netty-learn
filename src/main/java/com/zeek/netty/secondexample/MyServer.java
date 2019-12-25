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
            // 主要做一些赋值性的操作。其中.channel方法根据传递的参数创建了一个工厂。当调用下面的bind方法时，是通过该工厂创建出NioServerSocketChannel类的实例
            serverBootstrap.group(workerGroup, bossGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new MyServerInitializer());

            // sync方法确保『初始化』和『注册』操作均已经完成；换句话说调用sync后，程序会等待『初始化』和『注册』操作的完成，等这两个操作完成后，程序才继续向下执行
            // 如果不调用sync方法的话，调用了bind方法后会立刻返回，进行马上执行后边的代码，但是这就不能保证『初始化』和『注册』操作已经完成，程序有可能不能正常执行，因此sync方法必须调用
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            // 在实际的运行过程中，当服务端启动的时候，程序会停到下面一行的代码上；
            // 下面的sync方法的作用是当服务器关闭的时候，通过通过同步（和上面的sync方法效果一样，目的也是等待服务器完全关闭，在等待服务器关闭的过程中，程度也会停在这一行代码上）
            channelFuture.channel().closeFuture().sync();

        }finally {
            // 当服务器关闭完成后，下面的两行代码用于释放相关的服务器的资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}
