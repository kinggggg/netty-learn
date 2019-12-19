package com.zeek.netty.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * NioTest7
 * 直接内存缓冲区示例程序
 */
public class NioTest7 {

    public static void main(String[] args) throws IOException {
        
        ByteBuffer byteBuf = ByteBuffer.allocateDirect(512);

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

    }
}