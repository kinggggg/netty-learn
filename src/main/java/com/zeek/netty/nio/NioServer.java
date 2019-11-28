package com.zeek.netty.nio;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @ClassName NioServer
 * @Description
 * @Author liweibo
 * @Date 2019/11/28 5:28 PM
 * @Version v1.0
 **/
public class NioServer {

    private static Map<String, SocketChannel> clientMap = new HashMap<>();

    public static void main(String[] args) throws Exception{

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(8899));

        Selector selector = Selector.open();
        SelectionKey registerSelectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // keys为所有已经注册的通道
        Set<SelectionKey> keys = selector.keys();
        System.out.println("++++>已经注册的通道为: " + keys);

        while (true) {
            try {
                System.out.println("====>已经注册的通道为: " + keys);

                int numbers = selector.select();
                System.out.println("numbers: " + numbers);

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                System.out.println("0000>selectionKeys: " + selectionKeys);

                selectionKeys.forEach(selectionKey -> {
                    SocketChannel client;
                    try {
                        if (selectionKey.isAcceptable()) {
                            ServerSocketChannel server = (ServerSocketChannel)selectionKey.channel();
                            client = server.accept();
                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_READ);

                            String key = " ["+ UUID.randomUUID().toString() +"] " ;
                            clientMap.put(key, client);

                            System.out.println("获得客户端链接: " + client);

                        } else if (selectionKey.isReadable()) {

                            client = (SocketChannel)selectionKey.channel();

                            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                            int count = client.read(readBuffer);
                            if (count > 0) {
                                readBuffer.flip();
                                Charset charset = Charset.forName("utf-8");
                                String receiveMessage = String.valueOf(charset.decode(readBuffer).array());
                                System.out.println(client + ":" + receiveMessage);

                                String senderKey = null;
                                for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                    if (client == entry.getValue()) {
                                        senderKey = entry.getKey();
                                        break;
                                    }
                                }

                                for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                    SocketChannel value = entry.getValue();
                                    ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                    writeBuffer.put((senderKey + ":" + receiveMessage).getBytes());
                                    writeBuffer.flip();

                                    value.write(writeBuffer);
                                }
                            }

                            System.out.println("读取消息, 来自于: " + client);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                // 我感觉：这里调用clear方法不安全，应该只删除当前的selectionKey
                selectionKeys.clear();

                System.out.println("1111>selectionKeys: " + selectionKeys);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
