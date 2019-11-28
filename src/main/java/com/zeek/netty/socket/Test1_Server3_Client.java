package com.zeek.netty.socket;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Test1_Server3_Client
 * @Description
 * @Author liweibo
 * @Date 2019/11/27 5:43 PM
 * @Version v1.0
 **/
public class Test1_Server3_Client {

    public static void main(String[] args) throws IOException, InterruptedException {

//        List<Socket> socketList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Socket socket = new Socket("localhost", 8888);
//            socketList.add(socket);
            socket.close();
            System.out.println("客户端连接个数为: " + (i + 1));
        }

        Thread.sleep(90_000);

    }
}
