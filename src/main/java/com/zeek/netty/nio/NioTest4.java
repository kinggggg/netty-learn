package com.zeek.netty.nio;

import java.nio.ByteBuffer;

/**
 * NioTest4 
 * 放置进去的数据是什么杨的类型，获取的时候也必须是对应的类型
 */
public class NioTest4 {

    public static void main(String[] args) {
        
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        byteBuffer.putInt(10);
        byteBuffer.putChar('a');
        byteBuffer.putLong(123123L);
        
        byteBuffer.flip();

        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getLong());

    }
}