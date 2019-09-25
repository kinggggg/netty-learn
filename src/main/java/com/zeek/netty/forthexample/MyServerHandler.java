package com.zeek.netty.forthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @ClassName MyServerHandler
 * @Description 根据事件的状态，模拟客户端的心跳检测：
 *              当客户端在5秒时间内没有向服务器发送数据时，服务器就断开和此客户端的连接；
 *              当服务器端在7秒的时内没有向客户端发送数据的话，服务器就和此客户端断开连接；
 *              当客户端和服务器端在10秒的时间内没有任何数据发送的话，服务器就和此客户端断开连接；
 *              这里的5秒，7秒和10秒是在pipeline中添加IdleStateHandler时指定的
 *              pipeline.addLast(new IdleStateHandler(5, 7, 10, TimeUnit.SECONDS));
 * @Date 2019/9/25 7:52 PM
 * @Version v1.0
 **/
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;

            String eventType = null;
            switch (event.state()) {
                case READER_IDLE:
                    eventType = "读空闲" ;
                    break;
                case WRITER_IDLE:
                    eventType = "写空闲" ;
                    break;
                case ALL_IDLE:
                    eventType = "读写空闲" ;
                    break;
            }

            System.out.println(ctx.channel().remoteAddress() + " 超时事件：" + eventType);

            ctx.channel().close();
        }
    }
}
