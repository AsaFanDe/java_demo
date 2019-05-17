package com.asa.quartz;

/**
 * @Description
 * @Date 2019-05-16 9:46
 * @Author Asa
 * @Version 1.0
 **/
public class AsaTask implements Runnable{

    private String data;

    public AsaTask(String data) {
        this.data = data;
    }

    @Override
    public void run() {
        System.out.println(data);
    }
}
