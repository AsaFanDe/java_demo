package com.asa.bio;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * Created by AsaFan on 2018-09-09.
 */
public class ThreadLocalExample {
    private String a;

    private static ThreadLocal<Integer> seqCount = new ThreadLocal<Integer>(){
        // 实现initialValue()
        public Integer initialValue() {
            return 2;
        }
    };

     public int nextSeq(){
        seqCount.set(seqCount.get() + 1);
        return seqCount.get();
    }

    private static class SeqThread extends Thread{
        private ThreadLocalExample seqCount;

        SeqThread(ThreadLocalExample seqCount){
            this.seqCount = seqCount;
        }

        public void run() {
            for(int i = 0 ; i < 3 ; i++){
                System.out.println(Thread.currentThread().getName() + " seqCount :" + seqCount.nextSeq());
            }
        }
    }


    public static void main(String[] args){
        ThreadLocalExample seqCount = new ThreadLocalExample();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        SeqThread thread1 = new SeqThread(seqCount);
        SeqThread thread2 = new SeqThread(seqCount);
        SeqThread thread3 = new SeqThread(seqCount);
        SeqThread thread4 = new SeqThread(seqCount);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}
