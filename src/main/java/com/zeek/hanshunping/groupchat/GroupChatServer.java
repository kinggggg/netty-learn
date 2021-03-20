package com.zeek.hanshunping.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class GroupChatServer {

    // 定义属性
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;

    // 构造器
    // 初始化工作
    public GroupChatServer() {
        try {
            // 得到选择器
            selector = Selector.open();
            // ServerSocketChannel
            listenChannel = ServerSocketChannel.open();
            // 绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            // 设置非阻塞模式
            listenChannel.configureBlocking(false);
            // 将该listenChannel注册到selector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 监听
    public void listen() {
        try {
            // 循环处理
            while (true) {
                int count = selector.select();
                if (count > 0) { // 有事件处理
                    // 遍历得到selectionKey集合
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        //取出 selectionKey
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable()) {
                            SocketChannel socketChannel = listenChannel.accept();
                            socketChannel.configureBlocking(false);
                            // 将该 socketChannel注册到selector
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            // 提示
                            System.out.println(socketChannel.getRemoteAddress() + " 上线");
                        }
                        if (key.isReadable()) { // 通道发送read事件, 即通道是可读的状态
                            // 处理读
                            readData(key);
                        }
                        // 当前的key删除, 防止重复处理
                        iterator.remove();
                    }
                } else {
                    System.out.println("等待...");
                }
            } // while
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 发生异常处理
        }
    }

    // 读取客户端信息
    private void readData(SelectionKey key) {
        // 取到关联的channel
        SocketChannel channel = null;

        try {
            // 得到channel
            channel = (SocketChannel) key.channel();
            // 创建Buffer
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            int count = channel.read(byteBuffer);
            // 根据count的值做处理
            if (count > 0) {
                //把缓存的数据转成字符串
                String msg = new String(byteBuffer.array());
                // 输出该消息
                System.out.println("from 客户端: " + msg);
                // 向其它的客户端发消息(去掉自己), 专门写一个方法来处理
                sendInfoToOtherClients(msg, channel);
            }
        } catch (Exception e) {
            try {
                System.out.println(channel.getRemoteAddress() + "离线了...");
                // 取消注册
                key.cancel();
                // 关闭通道
                channel.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    // 转发消息给其他的客户端(通道)
    private void sendInfoToOtherClients(String msg, SocketChannel self) throws IOException {
        System.out.println("服务器转发消息中");

        // 遍历所有注册到selector上的SocketChannel并排除self
        for (SelectionKey key : selector.keys()) {
            // 通过key 取出对应的SocketChannel
            SelectableChannel targetChannel = key.channel();
            // 排除自己
            if (targetChannel instanceof SocketChannel && targetChannel != self) {
                // 转型
                SocketChannel dest = (SocketChannel) targetChannel;
                // 将msg存储到Buffer
                ByteBuffer buf = ByteBuffer.wrap(msg.getBytes());
                // 将Buffer的写入通道
                dest.write(buf);
            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
        System.out.println("服务器启动");
    }
}
