package com.asa.netty;

import io.netty.util.internal.PlatformDependent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 定时打印堆外内存
 * @Description
 * @Date 2019-09-10 10:33
 * @Author Asa
 * @Version 1.0
 **/

@Slf4j
@Component
public class DefaultDirectMemoryReporter {

    protected AtomicLong directMemory;

    @PostConstruct
    public void init() {
        Field field = ReflectionUtils.findField(PlatformDependent.class, "DIRECT_MEMORY_COUNTER");
        field.setAccessible(true);
        try {
            directMemory = (AtomicLong) field.get(PlatformDependent.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        this.startReport();
    }

    public void startReport() {
        Timer timer = new Timer();
        timer.schedule(new MyTimer(), 0, 1000);
    }

    private void report() {
        try {
            int memoryInKb = (int) (directMemory.get() / 1024);
            log.info("{}: {}k", "netty_direct_memory", memoryInKb);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

     class MyTimer extends TimerTask {

        @Override
        public void run() {
            report();
        }
    }
}
