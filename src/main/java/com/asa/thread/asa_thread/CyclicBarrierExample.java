package com.asa.thread.asa_thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Description 当线程到达某个状态后，暂停下来等待其他线程，所有线程均到达以后，继续执行。
 * @Date 2019-07-30 10:28
 * @Author Asa
 * @Version 1.0
 **/
public class CyclicBarrierExample {

    /**
     * 4个小伙伴约好先到西湖，再去吃饭
     * @param args
     */
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4);

        for (int i = 1; i <= 4; i++) {
            int partner = i;
            new Thread(() -> {
                System.out.println("小伙伴" + partner + "到达西湖");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println("小伙伴" + partner + "一起去吃饭");
            }).start();
        }
    }
}
