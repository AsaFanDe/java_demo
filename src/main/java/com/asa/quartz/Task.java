package com.asa.quartz;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.util.Date;

/**
 * @Description
 * @Date 2019-05-16 9:19
 * @Author Asa
 * @Version 1.0
 **/
@Slf4j
@Data
public class Task implements SchedulingConfigurer {
    private String cron;
    private AsaTask runnable;
    private String listenCron = "0/5 * * * * ?"; // 每隔5分钟检查一次是否到任务的执行时间

    public Task(String cron, AsaTask runnable) {
        this.cron = cron;
        this.runnable = runnable;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(runnable, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                // 任务触发，可修改任务的执行周期
                CronTrigger trigger = new CronTrigger(cron);
                Date nextExec = trigger.nextExecutionTime(triggerContext);
                long timePeriod  = nextExec.getTime() - System.currentTimeMillis();
                log.info(timePeriod + "");

                if (timePeriod < 0 || timePeriod > 5000) { // 任务执行的时间段不在5分钟内，重新设置nextExec
                    CronTrigger trigger2 = new CronTrigger(listenCron);
                    nextExec = trigger2.nextExecutionTime(triggerContext);
                    runnable.setRunFlag(false);
                }else {
                    runnable.setRunFlag(true);
                }
                return nextExec;
            }

        });
    }
}
