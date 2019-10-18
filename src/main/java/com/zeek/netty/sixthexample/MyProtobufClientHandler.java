package com.zeek.netty.sixthexample;

import com.zeek.netty.protobuf.DataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDate;
import java.util.Random;

/**
 * @ClassName MyProtobufClientHandler
 * @Description
 * @Date 2019/9/25 2:08 PM
 * @Version v1.0
 **/
public class MyProtobufClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int randomInt = new Random().nextInt(3);

        MyDataInfo.MyMessage myMessage = null;
        if (randomInt == 0) {
            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.PersonType)
                    .setPerson(MyDataInfo.Person.newBuilder()
                            .setName("张三")
                            .setAge(20)
                            .setAddress("北京").build())
                    .build();
        } else if(randomInt == 1) {
            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.DogType)
                    .setDog(MyDataInfo.Dog.newBuilder()
                            .setName("小狗")
                            .setAge(20).build())
                    .build();
        }else {
            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.CatType)
                    .setCat(MyDataInfo.Cat.newBuilder()
                            .setName("小猫")
                            .setCity("山东").build())
                    .build();
        }

        MyDataInfo.Person person = MyDataInfo.Person.newBuilder()
                .setName("张三")
                .setAge(20)
                .setAddress("北京")
                .build();

        ctx.writeAndFlush(myMessage);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


}
