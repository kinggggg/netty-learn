package com.zeek.netty.thrift;

import com.zeek.thrift.generated.DataException;
import com.zeek.thrift.generated.Person;
import com.zeek.thrift.generated.PersonService;
import org.apache.thrift.TException;

/**
 * @ClassName PersonServiceImpl
 * @Description
 * @Author liweibo
 * @Date 2019/11/6 6:01 PM
 * @Version v1.0
 **/
public class PersonServiceImpl implements PersonService.Iface {


    @Override
    public Person getPersonByUsername(String username) throws DataException, TException {
        System.out.println("Got Client Param: " + username);

        Person person = new Person();
        person.setUsername(username);
        person.setAge(20);
        person.setMarried(false);
        return person;
    }

    @Override
    public void savePerson(Person person) throws DataException, TException {
        System.out.println("Got Client Param:");

        System.out.println(person.getUsername());
        System.out.println(person.getAge());
        System.out.println(person.isMarried());
    }
}
