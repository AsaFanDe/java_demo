package com.asa.thread.asa_thread;

import java.util.concurrent.CountDownLatch;

/**
 * @Description 允许一个或多个线程一直等待，直到其他线程完成它们的操作。
 * @Date 2019-07-30 10:05
 * @Author Asa
 * @Version 1.0
 **/
public class CountDownLatchExample {

    /**
     * 洗衣服，要等倒入洗衣液才能洗
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                new Thread(() -> {
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("洗衣服");

                }).start();
            }

            if (i == 1) {
                new Thread(() -> {
                    System.out.println("加入洗衣液");
                    countDownLatch.countDown();
                }).start();
            }
        }
        countDownLatch.await();
    }
}
