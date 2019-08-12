package com.asa.quartz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Description
 * @Date 2019-05-16 9:54
 * @Author Asa
 * @Version 1.0
 **/
@Slf4j
@RestController
public class TaskController {

    @Autowired
    ApplicationContext applicationContext;

    @GetMapping("/task")
    public void task() throws Exception {
        Task task = applicationContext.getBean("task1", Task.class);
        String cron = task.getCron();
        CronTrigger trigger = new CronTrigger(cron);
        Date nextExec = trigger.nextExecutionTime(new SimpleTriggerContext());

        log.info(DateUtil.format2String(nextExec, DateUtil.yyMdHms));
        task.setCron("0 0/25 * * * ?");
        log.error("修改任务间隔时间");
    }
}
