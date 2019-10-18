package com.zeek.netty.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * @ClassName ProtobufTest
 * @Description
 * @Date 2019/10/18 2:21 PM
 * @Version v1.0
 **/
public class ProtobufTest {

    public static void main(String[] args) throws InvalidProtocolBufferException {

        DataInfo.Student student = DataInfo.Student.newBuilder()
                .setName("张三")
                .setAge(20)
                .setAddress("北京")
                .build();

        byte[] toByteArray = student.toByteArray();

        DataInfo.Student student1 = DataInfo.Student.parseFrom(toByteArray);

        System.out.println(student1.getName());
        System.out.println(student1.getAge());
        System.out.println(student1.getAddress());

    }
}
