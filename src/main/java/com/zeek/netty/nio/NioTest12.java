package com.zeek.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * NioTest12
 */
public class NioTest12 {

    public static void main(String[] args) throws IOException {
        
        int[] ports = new int[5];

        ports[0] = 5000;
        ports[1] = 5001;
        ports[2] = 5002;
        ports[3] = 5003;
        ports[4] = 5004;

        Selector selector = Selector.open();
        for (int i = 0; i < ports.length; i++) {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            ServerSocket serverSocket = serverSocketChannel.socket();
            InetSocketAddress address = new InetSocketAddress(ports[i]);
            serverSocket.bind(address);

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("监听端口: " + ports[i]);
        }

        // keys为所有已经注册的通道
        Set<SelectionKey> keys = selector.keys();
        System.out.println("++++>已经注册的通道为: " + keys);

        while (true) {

            System.out.println("====>已经注册的通道为: " + keys);

            int numbers = selector.select();
            System.out.println("numbers: " + numbers);

            // selectionKeys为所有的产生相应的事件后『激活』（通过阅读资料，在底层其实是操作系统完成的）的通道
            // 每当一个相应的事件后『激活』与该事件相关的SelectionKey会被添加到该集合中
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("0000>selectionKeys: " + selectionKeys);

            Iterator<SelectionKey> iter = selectionKeys.iterator();
            while (iter.hasNext()) {
                SelectionKey selectionKey = iter.next();

                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel)selectionKey.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);

                    socketChannel.register(selector, SelectionKey.OP_READ);

                    // 当处理完该激活的SelectionKey后，必须要移出掉！否则，该SelectionKey会被重复处理
                    // Removing the processed SelectionKey
                    // Having processed the SelectionKey, we're almost ready to return to the main loop.
                    // But first wemust remove the processed SelectionKey from the set of selected keys.
                    // If we do not removethe processed key, it will still be present as an activated key in the main set,
                    // which would lead us to attempt to process it again.
                    // We call the iterator's remove() method to remove the processed SelectionKey:
                    iter.remove();

                    System.out.println("获得客户端链接: " + socketChannel);
                }else if(selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel)selectionKey.channel();

                    int bytesRead = 0 ;
                    while (true) {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                        byteBuffer.clear();
                        
                        int read = socketChannel.read(byteBuffer);
                        if (read <= 0) {
                            break;
                        }

                        byteBuffer.flip();

                        socketChannel.write(byteBuffer);

                        bytesRead += read;
                    }

                    System.out.println("读取: " + bytesRead + ", 来自于: " + socketChannel);

                    // 同上的解释
                    iter.remove();
                }
            }

            System.out.println("1111>selectionKeys: " + selectionKeys);
        }



    }
}