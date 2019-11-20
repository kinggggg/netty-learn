package com.zeek.netty.nio;

import java.nio.ByteBuffer;

/**
 * NioTest5
 * 对ByteBuffer进行切片操作后生成的新的ByteBuffer与原来的ByteBuffer共享数据
 */
public class NioTest5 {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        for (int i = 0; i < byteBuffer.capacity(); i++) {
            byteBuffer.put((byte)i);
        }

        byteBuffer.position(2);
        byteBuffer.limit(6);

        ByteBuffer sliceBuffer = byteBuffer.slice();
        for (int i = 0; i < sliceBuffer.capacity(); i++) {
            byte b = sliceBuffer.get(i);
            sliceBuffer.put(i, (byte)(b * 2));
        }

        byteBuffer.position(0);
        byteBuffer.limit(byteBuffer.capacity());

        for (int i = 0; i < byteBuffer.capacity(); i++) {
            System.out.println(byteBuffer.get(i));
        }
    }
}