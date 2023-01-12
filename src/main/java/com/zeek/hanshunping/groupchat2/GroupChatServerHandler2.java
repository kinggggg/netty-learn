package com.zeek.hanshunping.groupchat2;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liweibo04 <liweibo04@58.com>
 * Created on 2023-01-12
 */
public class GroupChatServerHandler2 extends SimpleChannelInboundHandler<String> {

    // 如果要实现 1 对 1 单独聊天这种场景的话, 可以使用下面的数据结构
    // 使用一个 HashMap 管理用户信息与其 Channel 的对应关系. 例如 key 为用户 id, value 为对应的 Channel 对象
    //public static Map<String, Channel> channels = new HashMap<>();

    // 定义一个 Channel 组, 管理所有的 Channel
    // GlobalEventExecutor.INSTANCE 是全局的事件执行器, 是一个单例
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 表示连接建立, 一旦连接, 就会被执行(相当于其他的方法)
     * 将当前的 Channel 加入到 ChannelGroup
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 将该客户加入聊天的信息推送给其它在线的客户端
        // <b>该方法会将 channelGroup 中所有的 Channel 遍历, 并发送消息, 我们不需要自己遍历</b>
        channelGroup.writeAndFlush(sdf.format(new Date()) + "|" + "[客户端]" + channel.remoteAddress() + " 加入聊天\n");
        // 将当前的 Channel 加入到 ChannelGroup
        channelGroup.add(channel);
    }

    /**
     * 表示 Channel 处于活动状态, 提示 xx上线
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(sdf.format(new Date()) + "|" + ctx.channel().remoteAddress() + " 上线了~~\n");
    }

    /**
     * 表示 Channel 处于非活动状态, 提示 xx离线
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(sdf.format(new Date()) + "|" + ctx.channel().remoteAddress() + " 离线了~~\n");
    }

    /**
     * 断开连接, 将 xx 客户离开信息推送给当前在线的客户
     *
     * 有了这个方法后为什么还需要心跳检测(后续的课程中有讲到)呢?
     * 这是因为在手机强制关机或者手机切换为飞行模式时对服务端来说有可能是无感知的!因此需要心跳检测机制来探测客户端的连接状态
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(sdf.format(new Date()) + "|" + "[客户端]" + channel.remoteAddress() + " 离开了~~\n");

        System.out.println("channelGroup size " + channelGroup.size());

        // 当该 handlerRemoved 被调用时, 会自动从 channelGroup 中移除, 不需要执行下面的代码
        //channelGroup.remove(channel);
    }

    /**
     * 读取数据
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // 获取到当前 channel
        Channel channel = ctx.channel();
        // 这时我们遍历 channelGroup ,根据不同的情况, 回送不同的消息
        channelGroup.forEach(ch -> {
            // 不是当前的 Channel, 转发消息
            if (channel != ch) {
                ch.writeAndFlush(sdf.format(new Date()) + "|" + " [客户]" + channel.remoteAddress() + " 发送了消息" + msg + "\n");
            } else {
                // 回显自己发送的消息给自己
                ch.writeAndFlush(sdf.format(new Date()) + "|" + " [自己]发送了消息" + msg + "\n");
            }
        });
    }

    /**
     * 发生异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
