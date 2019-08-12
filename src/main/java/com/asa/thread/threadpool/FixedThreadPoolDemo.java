package com.asa.thread.threadpool;


import java.util.concurrent.Executors;

/**
 * @Description
 * @Date 2019-02-13 9:22
 * @Author Asa
 * @Version 1.0
 **/
public class FixedThreadPoolDemo {

    public static void main(String[] args) {
        Executors.newCachedThreadPool();
        Executors.newFixedThreadPool(1);
        Executors.newSingleThreadExecutor();
        Executors.newScheduledThreadPool(1);
        Executors.newWorkStealingPool(2);
        Executors.newSingleThreadScheduledExecutor();
    }
}
