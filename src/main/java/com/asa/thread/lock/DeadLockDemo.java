package com.asa.thread.lock;

/**
 * 死锁案例
 * @Description
 * @Date 2019-09-25 14:11
 * @Author Asa
 * @Version 1.0
 **/
public class DeadLockDemo {

    public static void main(String[] args) {
        Object lock1 = new Object();
        Object lock2 = new Object();

        Thread thread1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("get lock1");
                try {
                    Thread.sleep(2000);
                    synchronized (lock2) {
                        System.out.println("get lock2");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        Thread thread2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("get lock2");
                try {
                    Thread.sleep(1000);
                    synchronized (lock1) {
                        System.out.println("get lock2");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
