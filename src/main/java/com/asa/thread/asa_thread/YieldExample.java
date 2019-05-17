package com.asa.thread.asa_thread;

/**
 * Created by AsaFan on 2018-09-08.
 */
public class YieldExample {

    static class Producer extends Thread {
        public void run() {
            for (int i = 0; i < 5; i++) {
                System.out.println("I am Producer : Produced Item " + i);
                Thread.yield();
            }
        }
    }

    static class Consumer extends Thread{
        public void run(){
            for (int i = 0; i < 5; i++){
                System.out.println("I am Consumer : Consumed Item " + i);
                Thread.yield();
            }
        }
    }

    static class Consumer2 extends Thread{
        public void run(){
            for (int i = 0; i < 5; i++){
                System.out.println("I am Consumer2 : Consumed Item " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread producer = new Producer();
        Thread consumer = new Consumer();
        Thread consumer2 = new Consumer2();

        producer.setPriority(Thread.MIN_PRIORITY); //Min Priority
        consumer2.setPriority(Thread.NORM_PRIORITY);
        consumer.setPriority(Thread.MIN_PRIORITY); //Max Priority

        consumer2.start();
        System.out.println("2222222222222222222222");
        consumer2.join(1000);
        System.out.println("333333333333333333333");
        producer.start();
        // producer.join(1000);
        consumer.start();
    }

}
