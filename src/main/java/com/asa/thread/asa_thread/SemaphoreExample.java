package com.asa.thread.asa_thread;

import java.util.concurrent.Semaphore;

/**
 * @Description 可以控制同时访问的线程个数，它维护了一组**"许可证"**。
 * @Date 2019-07-30 10:19
 * @Author Asa
 * @Version 1.0
 **/
public class SemaphoreExample {

    /**
     * 买东西买单排队，一次只能容纳5个人买单
     * @param args
     */
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        int count = 50;
        for (int i = 0; i < count; i++) {
            int gk = i;
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println("顾客" + gk +"结账ing");
                    Thread.sleep(1000);
                    System.out.println("顾客" + gk +"结账");
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }
}
