package com.zeek.netty.bytebuf;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * AtomicIntegerFieldUpdater 使用
 **/
public class AtomicUpdaterTest {

    public static void main(String[] args) {

        Person person = new Person();

        // 下面的更新不具备原子性
//        for (int i = 0; i < 10; i++) {
//            Thread thread = new Thread(() -> {
//
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                System.out.println(person.age++);
//            });
//
//            thread.start();
//        }

        AtomicIntegerFieldUpdater<Person> atomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(Person.class, "age");
        for (int i = 0; i < 10; i++) {

            Thread thread = new Thread(() -> {

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(atomicIntegerFieldUpdater.getAndIncrement(person));

            });
            thread.start();
        }
    }
}

class Person {
    volatile public int age = 1;
}
