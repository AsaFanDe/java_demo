package com.asa.thread.StopThread;

/**
 * 如何正确的停止一个线程
 * Created by AsaFan on 2018-09-07.
 */
public class StopThread extends Thread{
    // 用于停止线程
    public volatile boolean stopMe = true;


    @Override
    public void run() {
        int i = 0;
        while (!this.isInterrupted()) {
            System.out.println(i);
            i++;
           try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
               e.printStackTrace();
               //System.out.println(this.isInterrupted());
                if (!this.isInterrupted()) {
                    this.interrupt();
                    System.out.println(this.isInterrupted());
                }
            }
            // 让出CPU，给其他线程执行
            Thread.yield();
        }
            System.out.println("线程中断");


    }

    public static void main(String[] args) {
        StopThread stopThread = new StopThread();
        stopThread.start();

        long startTime = System.currentTimeMillis();
        try {
            Thread.sleep(1000);
            stopThread.interrupt();
            long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);

            System.out.println("#######################################3");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
