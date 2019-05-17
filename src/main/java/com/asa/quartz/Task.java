package com.asa.quartz;

import lombok.Data;
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
@Data
public class Task implements SchedulingConfigurer {
    private String cron;
    private Runnable runnable;

    public Task(String cron, Runnable runnable) {
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
                return nextExec;
            }
        });
    }
}
