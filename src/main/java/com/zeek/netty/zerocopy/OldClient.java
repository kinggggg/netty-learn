package com.zeek.netty.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;


/**
 * OldClient
 */
public class OldClient {

    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("localhost", 8899);

        String fileName = "/Users/weibo_li/Desktop/46-60/50_零拷贝实例深度剖析.mp4" ;
        InputStream inputStream = new FileInputStream(fileName);

        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        long startTime = System.currentTimeMillis();

        byte[] buffer = new byte[4096];
        long readCount;
        long total = 0;

        while ((readCount = inputStream.read(buffer)) >=0) {
            total += readCount;
            dataOutputStream.write(buffer);
        }

        System.out.println("发送字节数: " + total + ", 耗时： " + (System.currentTimeMillis() - startTime));

        dataOutputStream.close();
        socket.close();
        inputStream.close();
    }
}