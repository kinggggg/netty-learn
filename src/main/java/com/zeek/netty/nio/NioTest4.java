package com.zeek.netty.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * NioTest4 
 * 放置进去的数据是什么杨的类型，获取的时候也必须是对应的类型
 * 获取的顺序必须一样
 */
public class NioTest4 {

    public static void main(String[] args) throws IOException {

        ByteBuffer byteBuf = ByteBuffer.allocate(512);

        FileInputStream inputStream = new FileInputStream("input.txt");
        FileOutputStream outputStream = new FileOutputStream("output.txt");

        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();

        while (true) {
            byteBuf.clear(); // 当注释掉了这行后，下面一样代码的返回值为0！此时Position和Limit的值相等
            int read = inputChannel.read(byteBuf);
            if (read == -1) {
                break;
            }

            byteBuf.flip();
            outputChannel.write(byteBuf);
        }

        outputChannel.close();
        outputStream.close();
        inputChannel.close();
        inputStream.close();
        
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
