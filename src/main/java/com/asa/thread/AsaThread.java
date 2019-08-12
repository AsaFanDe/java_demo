package com.asa.thread;

import sun.misc.Unsafe;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Created by AsaFan on 2018-09-07.
 */
public class AsaThread {

    public static void main(String[] args) {
        // 所有同步器的父类
        AbstractQueuedSynchronizer abstractQueuedSynchronizer = new AbstractQueuedSynchronizer() {
            @Override
            protected boolean isHeldExclusively() {
                return super.isHeldExclusively();
            }
        };
        // ExecutorService 线程池
        ThreadPoolExecutor cachedThreadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        // 用于在任意内存地址位置读写数据，还支持CAS， 只能从引导类加载器（bootstrap class loader）加载
        Unsafe the = Unsafe.getUnsafe();
    }
}
