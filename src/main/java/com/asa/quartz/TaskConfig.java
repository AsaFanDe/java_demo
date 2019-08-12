package com.asa.quartz;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Date 2019-05-16 9:27
 * @Author Asa
 * @Version 1.0
 **/
@Configuration
public class TaskConfig {

    @Bean(name="task1")
    public Task task1() {
        return new Task("0 0/30 * * * ?", new AsaTask("任务一", false));
    }

   /* @Bean("task2")
    public Task task2() {
        return new Task("0/5 * * * * ?", new AsaTask("任务二"));
    }*/

   /* @Bean
    public Task task2() {
        return new Task("0/10 * * * * ?", new AsaTask("任务二"));
    }*/


}
