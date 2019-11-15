package com.zeek.netty.decorator;

/**
 * @ClassName ConcreteDecorator1
 * @Description
 * @Author liweibo
 * @Date 2019/11/15 3:07 PM
 * @Version v1.0
 **/
public class ConcreteDecorator2 extends Decorator {


    public ConcreteDecorator2(Component component) {
        super(component);
    }

    @Override
    public void doSomething() {
        super.doSomething();
        this.doAnotherThing();

    }

    private void doAnotherThing() {
        System.out.println("功能C");
    }
}
