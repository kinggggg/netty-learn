package com.zeek.netty.nio;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName NioTest2
 * @Description
 * @Author liweibo
 * @Date 2019/11/15 4:44 PM
 * @Version v1.0
 **/
public class NioTest2 {

    public static void main(String[] args) throws IOException {

        FileInputStream fileInputStream = new FileInputStream("NioTest2.txt");
        FileChannel fileChannel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        fileChannel.read(byteBuffer);

        byteBuffer.flip();

        while (byteBuffer.hasRemaining()) {
            byte b = byteBuffer.get();
            System.out.println("Character:" + (char)b);
        }

        fileInputStream.close();


    }
}
