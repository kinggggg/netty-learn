package com.zeek.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;

// 泛型类型说明了调用该解码器时输入信息的类型
public class MyLongToStringDecoder extends MessageToMessageDecoder<Long> {

    @Override
    protected void decode(ChannelHandlerContext ctx, Long msg, List<Object> out) throws Exception {
        System.out.println("MyLongToStringDecoder invoked!");

        // 这里说明解码器输出内容的类型
        out.add(String.valueOf(msg));
    }
}
