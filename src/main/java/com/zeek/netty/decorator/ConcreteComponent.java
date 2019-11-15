package com.zeek.netty.decorator;

/**
 * @ClassName ConcreteComponent
 * @Description
 * @Author liweibo
 * @Date 2019/11/15 3:04 PM
 * @Version v1.0
 **/
public class ConcreteComponent implements Component {

    @Override
    public void doSomething() {
        System.out.println("功能A");
    }
}
