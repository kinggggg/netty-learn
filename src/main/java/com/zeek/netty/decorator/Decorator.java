package com.zeek.netty.decorator;

/**
 * @ClassName Decorator
 * @Description
 * @Author liweibo
 * @Date 2019/11/15 3:05 PM
 * @Version v1.0
 **/
public class Decorator implements Component {

    private Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void doSomething() {
        component.doSomething();
    }
}
