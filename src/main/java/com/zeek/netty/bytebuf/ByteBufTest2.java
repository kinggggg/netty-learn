package com.zeek.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

public class ByteBufTest2 {

    public static void main(String[] args) {
        CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();

        ByteBuf byteBuf =  Unpooled.buffer(10);
        ByteBuf directBuf = Unpooled.directBuffer();

        compositeByteBuf.addComponent(byteBuf);
        compositeByteBuf.addComponent(directBuf);

        compositeByteBuf.forEach(System.out::println);

        // 可以删除当中的一个组件（元素）;
        compositeByteBuf.removeComponent(0);

        // 删除第一个元素（类型是堆缓冲区）后，只剩下一个元素（类型为直接缓冲区）
        compositeByteBuf.forEach(System.out::println);
    }
}
