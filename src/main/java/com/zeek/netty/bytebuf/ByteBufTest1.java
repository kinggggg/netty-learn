package com.zeek.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

public class ByteBufTest1 {

    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("helloworld", Charset.forName("utf-8"));

        if (byteBuf.hasArray()) {
            byte[] content = byteBuf.array();
            System.out.println(new String(content, Charset.forName("utf-8")));

            /**
             * helloworld
             */
            System.out.println(byteBuf);

            /**
             * 0
             * 0
             * 10
             * 30
             * 10
             */
            System.out.println(byteBuf.arrayOffset());
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.capacity());
            System.out.println(byteBuf.readableBytes());

            System.out.println("0. ---------------------------");

            /**
             * 0
             * 10
             * 10
             */
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.readableBytes());

            /**
             *  正常输出
             * h
             * readableBytes: 10
             * e
             * readableBytes: 10
             * l
             * readableBytes: 10
             * l
             * readableBytes: 10
             * o
             * readableBytes: 10
             * w
             * readableBytes: 10
             * o
             * readableBytes: 10
             * r
             * readableBytes: 10
             * l
             * readableBytes: 10
             * d
             * readableBytes: 10
             */
            for (int i = 0; i < byteBuf.readableBytes(); i++) {
                // getByte(index) 是绝对读取，readerIndex和writerIndex值不变
                System.out.println((char) byteBuf.getByte(i));
                // 通过绝对读取的方式读取内容时，byteBuf.readableBytes()的值不变；下面的输出一直为10
                System.out.println("readableBytes: " + byteBuf.readableBytes());
            }

            System.out.println("1. ---------------------------");

            /**
             * 0
             * 10
             * 10
             */
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.readableBytes());

            /**
             * h
             * readableBytes: 9
             * e
             * readableBytes: 8
             * l
             * readableBytes: 7
             * l
             * readableBytes: 6
             * o
             * readableBytes: 5
             */
            for (int i = 0; i < byteBuf.readableBytes(); i++) {
                System.out.println((char) byteBuf.readByte());
                // 通过相对读取的方式读取内容时，readerIndex随着读取的操作而逐步减少（因为byteBuf.readByte()方法会增加readerIndex的值，writerIndex的值不变）
                // byteBuf.readableBytes()的值随着读取次数而逐渐减少（即，byteBuf.readableBytes() = writerIndex - writerIndex）
                // 当循环结束瘦readerIndex的值为5
                System.out.println("readableBytes: " + byteBuf.readableBytes());
            }

            System.out.println("2. ---------------------------");

            /**
             * byteBuf.readerIndex()的值为5是因为上面的输出中使用的是相对读取的方式，会改变readerIndex的值上面循环完成后readerIndex的值为5
             * 5
             * 10
             * 5
             */
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.readableBytes());

            /**
             * 由于 byteBuf.readableBytes()的值为5，因此会从下标为0到5读取content数组的内容
             * h
             * e
             * l
             * l
             * o
             */
            for (int i = 0; i < byteBuf.readableBytes(); i++) {
                System.out.println((char) byteBuf.getByte(i));
            }
        }



    }
}
