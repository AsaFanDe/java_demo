package com.asa.thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Description
 * @Date 2019-09-26 15:01
 * @Author Asa
 * @Version 1.0
 **/
public class ReentrantReadWriteLockDemo {

    public static Integer count = 0;

    public static void main(String[] args) throws InterruptedException {
        ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
        int a = 0;
        Lock readLock = rwLock.readLock();
        Lock writeLock = rwLock.writeLock();
        for (int i = 0; i < 1; i++) {
            Thread thread = new Thread(() -> {
                writeLock.lock();
                System.out.println(Thread.currentThread().getName() + "write lock");
                try {
                    Thread.sleep(2000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                readLock.lock();
                System.out.println(Thread.currentThread().getName() + ":" + count);
                readLock.unlock();
                writeLock.unlock();

            });
            thread.start();
        }
        Thread.sleep(1000);

        readLock.lock();
        System.out.println(Thread.currentThread().getName() + ":" + count);
        readLock.unlock();


        Thread.sleep(5000 );
    }
    /*public static void main(String[] args) {
        int i = 1 << 16;
        System.out.println(655396 >>> 16);
        System.out.println(1 & i);
        System.out.println(1 & (i - 1));
        System.out.println(65535 & (i - 1));
        System.out.println(655396 & (i - 1));
    }*/
}
