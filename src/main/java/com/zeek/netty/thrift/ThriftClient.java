package com.zeek.netty.thrift;

import com.zeek.thrift.generated.Person;
import com.zeek.thrift.generated.PersonService;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * @ClassName ThriftClient
 * @Description
 * @Author liweibo
 * @Date 2019/11/6 6:19 PM
 * @Version v1.0
 **/
public class ThriftClient {


    public static void main(String[] args) {

        TTransport tTransport = new TFramedTransport(new TSocket("localhost", 8899), 600);
        TProtocol protocol = new TCompactProtocol(tTransport);
        PersonService.Client client = new PersonService.Client(protocol);

        try {
            tTransport.open();

            Person person = client.getPersonByUsername("张三");
            System.out.println(person.getUsername());
            System.out.println(person.getAge());
            System.out.println(person.isMarried());
            System.out.println("------------------");

            Person person2 = new Person();
            person2.setUsername("李四");
            person2.setAge(10);
            person2.setMarried(true);

            client.savePerson(person2);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }finally {
            tTransport.close();
        }

    }

}
