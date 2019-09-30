package com.asa.base;

/**
 * @Description 如果类声明了finalize方法
 *              垃圾收集器会在释放这个实例之前执行这个方法
 * @Date 2019-09-30 9:15
 * @Author Asa
 * @Version 1.0
 **/
public class FinalizeDemo {

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("被释放");
    }

    public static void main(String[] args) throws InterruptedException {
        FinalizeDemo finalize = new FinalizeDemo();
        finalize = null;
        int i = 0;
        for (;;) {
            Thread.sleep(1000L);
            System.out.println(i);
            if (i == 5) {
                System.gc();
            }
            i++;
        }
    }
}
