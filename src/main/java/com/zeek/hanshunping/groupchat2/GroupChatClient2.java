package com.zeek.hanshunping.groupchat2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @author liweibo04 <liweibo04@58.com>
 * Created on 2023/1/12 15:16
 */
public class GroupChatClient2 {

    private final String host;

    private final int port;

    public GroupChatClient2(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());
                            pipeline.addLast(new GroupChatClientHandler2());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            // 得到 Channel
            Channel channel = channelFuture.channel();
            System.out.println("-----" + channel.localAddress() + "--------");
            // 客户端需要输入信息, 创建一个扫描器
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String msg = scanner.nextLine();
                // 通过 Channel 发送到服务器端
                channel.writeAndFlush(msg + "\r\n");
            }
        }finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new GroupChatClient2("127.0.0.1", 7000).run();
    }
}
