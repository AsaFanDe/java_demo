package com.asa.thread.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description
 * @Date 2019-09-24 10:29
 * @Author Asa
 * @Version 1.0
 **/
public class ReentrantLockDemo {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }

                lock.lock();
                System.out.println(Thread.currentThread().getName());
                lock.unlock();
            });
            thread.start();
        }
    }
}
