package com.zeek.netty.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;

/**
 * @ClassName Test1_Server3
 * @Description
 * @Author liweibo
 * @Date 2019/11/27 5:39 PM
 * @Version v1.0
 **/
public class Test1_Server3 {

    public static void main(String[] args) throws IOException, InterruptedException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", 8888), 60);
        ServerSocket serverSocket = serverSocketChannel.socket();

        Socket socket = serverSocket.accept();

//        boolean isRun = true;
//        while (isRun == true) {
//            Socket socket = serverSocket.accept();
//            socket.close();
//        }

        Thread.sleep(800_000);
        serverSocket.close();
        serverSocketChannel.close();

    }
}
