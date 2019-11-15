package com.zeek.netty.grpc;

import com.zeek.netty.proto.MyRequest;
import com.zeek.netty.proto.MyResponse;
import com.zeek.netty.proto.StudentServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * @ClassName GrpcClient
 * @Description
 * @Author liweibo
 * @Date 2019/11/14 7:20 PM
 * @Version v1.0
 **/
public class GrpcClient {

    public static void main(String[] args) {

        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8899).usePlaintext(true).build();
        StudentServiceGrpc.StudentServiceBlockingStub blockingStub = StudentServiceGrpc.newBlockingStub(managedChannel);
        MyResponse response = blockingStub.getRealNameByUsername(MyRequest.newBuilder().setUsername("zhangsan").build());
        System.out.println(response.getRealname());

    }
}
