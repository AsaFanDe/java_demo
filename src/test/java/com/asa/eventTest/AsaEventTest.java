package com.asa.eventTest;

import com.asa.event.AsaEntity;
import com.asa.event.AsaEvent;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description
 * @Date 2019-05-31 9:59
 * @Author Asa
 * @Version 1.0
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AsaEventTest {

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Test
    public void test() throws InterruptedException {
        eventPublisher.publishEvent(new AsaEvent(this, new AsaEntity("asa", "18")));
        log.info("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        log.info(Thread.currentThread().getName());

        Thread.sleep(20000l);
    }

}
