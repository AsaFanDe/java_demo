package com.asa.thread.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @className: AtomicDemo
 * @Description //TODO
 * @Date 2018-09-25 9:10
 * @Author Asa
 * @Version 1.0
 **/
@Slf4j
public class AtomicDemo {

    public void atomicBooleanTest(){
        AtomicBoolean ab = new AtomicBoolean();
        ab.compareAndSet(false, true);
        log.info(ab.get() + "");
        ab.compareAndSet(true, false);
        log.info(ab.get() + "");
        AtomicBoolean ab2 = new AtomicBoolean();
        log.info(ab2.get() + "");
     }

     public void atomicInterer() {
         AtomicInteger ai = new AtomicInteger();
         log.info(ai.addAndGet(1) + "");
         log.info(ai.addAndGet(2) + "");
         ai.compareAndSet(3, 4);
         log.info(ai.get() + "");
         ai.set(5);
         log.info(ai.get() + "");
     }

     public static void main(String[] args) {
         AtomicDemo demo = new AtomicDemo();
         // demo.atomicBooleanTest();
         demo.atomicInterer();
     }
}
