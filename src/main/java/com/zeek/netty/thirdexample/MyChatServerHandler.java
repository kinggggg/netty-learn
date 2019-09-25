package com.zeek.netty.thirdexample;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @ClassName MyChatServerHandler
 * @Description
 * @Date 2019/9/25 3:07 PM
 * @Version v1.0
 **/
public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {

    // 保存所有已经与服务器端已经建立好的链接的客户端
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();

        channelGroup.forEach(ch -> {
            if(channel != ch) {
                ch.writeAndFlush(channel.remoteAddress() + " 发送的消息：" + msg + "\n");
            }else {
                ch.writeAndFlush("【自己】" + msg + "\n");
            }
        });
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("handlerAdded");

        // 向所有的客户端发送消息
        channelGroup.writeAndFlush("【服务器】- " + channel.remoteAddress() + " 加入\n");
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("handlerRemoved");

        channelGroup.writeAndFlush("【服务器】- " + channel.remoteAddress() + " 离开\n");
        // 当某个客户端断开连接后Netty会自动地将其从channelGroup中移除！！！！很神奇。当然也可以显示进行remove
        //channelGroup.remove(channel);

        System.out.println(channelGroup.size());

    }

    // 连接处于活动状态
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("channelActive");
        System.out.println(channel.remoteAddress() + " 上线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("channelInactive");
        System.out.println(channel.remoteAddress() + " 下线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
