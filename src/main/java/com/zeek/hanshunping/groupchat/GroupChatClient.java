package com.zeek.hanshunping.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class GroupChatClient {
    // 定义相关的属性
    private final String HOST = "127.0.0.1";// 服务器的ip
    private final int PORT = 6667;// 服务器端口
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    // 构造器,完成初始化工作
    public GroupChatClient() throws IOException {
        selector = Selector.open();
        //连接服务器
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
        // 设置非阻塞
        socketChannel.configureBlocking(false);
        // 将channel 注册到selector
        socketChannel.register(selector, SelectionKey.OP_READ);
        // 得到username
        username = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(username + " is ok...");
    }

    // 向服务器发送消息
    public void sendInfo(String info) {
        info = username + " 说: " + info;

        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 读取从服务器回复的消息
    public void readInfo() {
        try {
            int readChannels = selector.select();
            if (readChannels > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        // 得到相关通道
                        SocketChannel sc = (SocketChannel) key.channel();
                        // 得到一个Buffer
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        // 读取
                        sc.read(buffer);
                        // 把读取到的缓冲区的数据转换成字符串
                        String msg = new String(buffer.array());
                        System.out.println(msg.trim());
                    }
                    // remove的操作放在这两个地方均可以
                    iterator.remove();// 删除当前的selectionKey, 防止重复操作
                }
//                iterator.remove();// 删除当前的selectionKey, 防止重复操作
            } else {
                //
            }
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) throws IOException {
        GroupChatClient groupChatClient = new GroupChatClient();

        // 启动一个线程, 每隔3秒, 读取从服务器发送的数据
        new Thread(){
            @Override
            public void run() {
                while (true) {
                    groupChatClient.readInfo();
                    try {
                        Thread.currentThread().sleep(3000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        // 发送数据给服务器端
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            groupChatClient.sendInfo(s);
        }
    }
}
