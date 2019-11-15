package com.zeek.netty.decorator;

/**
 * @ClassName Client
 * @Description
 * @Author liweibo
 * @Date 2019/11/15 3:09 PM
 * @Version v1.0
 **/
public class Client {

    public static void main(String[] args) {

        Component component = new ConcreteDecorator2(new ConcreteDecorator1(new ConcreteComponent()));
        component.doSomething();

    }
}
