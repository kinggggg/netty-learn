package com.zeek.netty.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName NioTest3
 * @Description
 * @Author liweibo
 * @Date 2019/11/15 4:50 PM
 * @Version v1.0
 **/
public class NioTest3 {

    public static void main(String[] args) throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream("NioTest3.txt");
        FileChannel fileChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        byte[] messages = "hello world welcome, nihao".getBytes();
        ;

        for (int i = 0; i < messages.length; i++) {
            byteBuffer.put(messages[i]);
        }

        byteBuffer.flip();

        fileChannel.write(byteBuffer);

        fileOutputStream.close();


        /**
         *
         * 下面程序的输出如下：
         *
         * Write mode:
         * 	Capacity: 10
         * 	Position: 2
         * 	Limit: 10
         * Flip
         * Read mode:
         * 	Capacity: 10
         * 	Position: 0
         * 	Limit: 2
         * Begin read:
         * 	Character:10
         * 	Character:101
         * End read:
         * Read mode:
         * 	Capacity: 10
         * 	Position: 2
         * 	Limit: 2
         * Flip
         * Read mode:
         * 	Capacity: 10
         * 	Position: 0
         * 	Limit: 2
         * Exception in thread "main" java.nio.BufferOverflowException
         * 	at java.nio.Buffer.nextPutIndex(Buffer.java:521)
         * 	at java.nio.HeapIntBuffer.put(HeapIntBuffer.java:169)
         * 	at com.zeek.netty.nio.NioTest3.main(NioTest3.java:80)
         *
         * Process finished with exit code 1
         *
         *
         *
         **/
        IntBuffer intBuffer = IntBuffer.allocate(10);
        intBuffer.put(10);
        intBuffer.put(101);
        /**
         * 程序的输出如下：
         * Write mode:
         * 	Capacity: 10    容量为10
         *  Position: 2     已经写入了2个元素，此时Position的含义与Javadoc中的说明一样：is the index of the next element to be
         *                              read or written（第一个可以读或者写的元素，在这里为可写的第一个元素的位置）
         *  Limit: 10       当初始化的时候Limit的值与Capacity的值相等
         **/
        System.err.println("Write mode: ");
        System.err.println("\tCapacity: " + intBuffer.capacity());
        System.err.println("\tPosition: " + intBuffer.position());
        System.err.println("\tLimit: " + intBuffer.limit());

        /**
         * 程序的输出如下：
         * Flip
         * Read mode:
         *  Capacity: 10    容量为10
         *  Position: 0     当从写状态进入到读状态时Position变为0（通过查看flip方法的源码得：到从写状态到读状态时flip方法会先将Position的值赋值给Limit，让后将Position置为0）
         *                              ；此时Position的含义与Javadoc中的说明也是一样的is the index of the next element to be read or written（不过，这里为可以读的第一个元素）
         *  Limit: 2        从写状态到读状态时flip方法会先将Position的值赋值给Limit，让后将Position置为0
         **/
        System.err.println("Flip");
        intBuffer.flip();
        System.err.println("Read mode: ");
        System.err.println("\tCapacity: " + intBuffer.capacity());
        System.err.println("\tPosition: " + intBuffer.position());
        System.err.println("\tLimit: " + intBuffer.limit());

        /**
         * 程序的输出如下：
         * Begin read:
         * 	Character:10
         * 	Character:101
         * End read:
         **/
        System.err.println("Begin read: ");
        while (intBuffer.hasRemaining()) {
            int i = intBuffer.get();
            System.err.println("\tCharacter:" + i);
        }
        System.err.println("End read: ");

        /**
         * 程序的输出如下:
         * Read mode:
         * 	Capacity: 10    容量为10
         * 	Position: 2     已经读取了2个元素，注意此时Position的含义就和Javadoc中的含义不一样了！此时Position的含义已经不是可以读或者可以写
         * 	                的第一个元素了，如果此时再调用intBuffer.get()的话，程序会报错
         * 	Limit: 2
         **/
        System.err.println("Read mode: ");
        System.err.println("\tCapacity: " + intBuffer.capacity());
        System.err.println("\tPosition: " + intBuffer.position());
        System.err.println("\tLimit: " + intBuffer.limit());

        /**
         * 程序的输出如下:
         * Flip
         * Read mode:
         * 	Capacity: 10    容量10
         * 	Position: 0     根据flip方法的源码：首先将Position的值赋值给Limit，然后将Position的值置为0
         * 	Limit: 2        (同上)
         **/
        System.err.println("Flip");
        intBuffer.flip();
        System.err.println("Read mode: ");
        System.err.println("\tCapacity: " + intBuffer.capacity());
        System.err.println("\tPosition: " + intBuffer.position());
        System.err.println("\tLimit: " + intBuffer.limit());

        // 正常put，因为Put之前Position为0，Limit为2
        System.err.println("put(10)之前:");
        System.err.println("\tCapacity: " + intBuffer.capacity());
        System.err.println("\tPosition: " + intBuffer.position());
        System.err.println("\tLimit: " + intBuffer.limit());
        intBuffer.put(10);
        System.err.println("put(10)之后:");
        System.err.println("\tCapacity: " + intBuffer.capacity());
        System.err.println("\tPosition: " + intBuffer.position());
        System.err.println("\tLimit: " + intBuffer.limit());
        // 正常put，因为Put之前Position为1，Limit为2
        System.err.println("put(101)之前:");
        System.err.println("\tCapacity: " + intBuffer.capacity());
        System.err.println("\tPosition: " + intBuffer.position());
        System.err.println("\tLimit: " + intBuffer.limit());
        intBuffer.put(101);
        System.err.println("put(101)之后:");
        System.err.println("\tCapacity: " + intBuffer.capacity());
        System.err.println("\tPosition: " + intBuffer.position());
        System.err.println("\tLimit: " + intBuffer.limit());
        // 抛出异常，因为Put之前Position为2，Limit为2，可见此时虽然Position与Limit的值相等，但是程序优先判断的是Limit的值，即：
        // Limit指向的值为『is the index of the first element that should not be read or written』翻译过来为第一个
        // 不能读或者写的元素
        intBuffer.put(102);

    }
}
