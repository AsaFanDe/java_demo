package com.asa.event;

import com.asa.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

/**
 * @Description
 * @Date 2019-05-31 9:52
 * @Author Asa
 * @Version 1.0
 **/
@Slf4j
@Configuration
public class AsaConfig {

    @Async
    @EventListener
    public void handleCustomEvent(AsaEvent customEvent) {
        //监听 CustomEvent事件
        /*log.info("监听到CustomEvent事件，消息为：{}, 发布时间：{}",
                customEvent.getAsaEntity(), customEvent.getTimestamp());*/
        log.info(Thread.currentThread().getName());
        throw new BusinessException("cccccccccccc");
        // log.info("++++++++++++++++++++++++++");
    }
}
