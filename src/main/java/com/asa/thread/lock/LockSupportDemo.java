package com.asa.thread.lock;

import java.util.concurrent.locks.LockSupport;

/**
 * @Description
 * @Date 2019-02-13 9:11
 * @Author Asa
 * @Version 1.0
 **/
public class LockSupportDemo {
    public void LockSupportTest() {
        Person person = new Person();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                person.walk();
            }
        },"Jason");
        thread.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("三秒过去，我解救"+thread.getName());
        LockSupport.unpark(thread);//解除该线程阻塞
    }

    class Person {
        public void walk() {
            Thread currentThread = Thread.currentThread();
            System.out.println(currentThread.getName() + "在走。。。。前面有人挡住了");
            // 1s = 1*10^9ns
            LockSupport.parkNanos(1000000000L); // 阻塞 n 微妙
            // LockSupport.parkUntil(System.currentTimeMillis() + 2000L);// 阻塞到XX时候
            System.out.println(currentThread.getName() + "又可以走了");
        }
    }

    public static void main(String[] args) {
        LockSupportDemo demo = new LockSupportDemo();
        demo.LockSupportTest();
    }
}
