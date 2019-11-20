package com.zeek.netty.nio;

import java.nio.ByteBuffer;

/**
 * NioTest6
 * 只读buffer，我们可以随时将一个普通的Buffer调用asReadOnlyBuffer方法返回一个只读Buffer
 * 但是不能将一个只读的Buffer转化为读写的Buffer
 */
public class NioTest6 {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        // java.nio.HeapByteBuffer
        System.out.println(buffer.getClass());

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte)i);
        }

        ByteBuffer readOnly = buffer.asReadOnlyBuffer();
        // java.nio.HeapByteBufferR
        System.out.println(readOnly.getClass());
        // 抛出异常，只读的不能写入数据
        readOnly.put((byte)2);
        

    }
}