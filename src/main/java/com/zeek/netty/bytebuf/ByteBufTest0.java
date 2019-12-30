package com.zeek.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @ClassName ByteBufTest0
 * @Description
 * @Author liweibo
 * @Date 2019/12/27 10:27 AM
 * @Version v1.0
 **/
public class ByteBufTest0 {

    public static void main(String[] args) {

        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }

        for (int i = 0; i < 10; i++) {
            System.out.println(buffer.getByte(i));
        }



    }
}
