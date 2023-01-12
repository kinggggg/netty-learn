package com.zeek.hanshunping.groupchat2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author liweibo04 <liweibo04@58.com>
 * Created on 2023-01-12
 */
public class GroupChatClientHandler2 extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg.trim());
    }
}
