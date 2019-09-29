package com.asa.thread.threadpool;


import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @Description 线程池
 * @Date 2019-02-13 9:22
 * @Author Asa
 * @Version 1.0
 **/
public class ThreadPoolDemo {

    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());
        executorService.execute(() -> {
            System.out.println("8888888888888888888888");
        });
    }
}
