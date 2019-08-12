package com.asa.quartz;

import lombok.Data;

/**
 * @Description
 * @Date 2019-05-16 9:46
 * @Author Asa
 * @Version 1.0
 **/
@Data
public class AsaTask implements Runnable{

    private String data;
    private boolean runFlag;

    public AsaTask(String data, boolean runFlag) {
        this.data = data;
        this.runFlag = runFlag;
    }

    @Override
    public void run() {
        if (runFlag) {
            System.out.println(data);
        }else {
            System.out.println("未到执行时间");
        }
    }
}
