package com.asa.quartz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void task() {
        Task task = applicationContext.getBean(Task.class);
        task.setCron("0/15 * * * * ?");
        log.error("修改任务间隔时间");
    }
}
