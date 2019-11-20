package com.zeek.netty.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest4 {

    public static void main(String[] args) throws IOException {
        ByteBuffer byteBuf = ByteBuffer.allocate(512);

        FileInputStream inputStream = new FileInputStream("input.txt");
        FileOutputStream outputStream = new FileOutputStream("output.txt");

        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();

        while (true) {
            byteBuf.clear();
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
