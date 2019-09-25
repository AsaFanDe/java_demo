package com.asa.design_demo.proxy;

/**
 * @Description
 * @Date 2019-09-25 11:00
 * @Author Asa
 * @Version 1.0
 **/
public class StaticProxyDemo {
    public static void main(String[] args) {
        StaticSubject staticSubject = new StaticSubject(new DefaultSubject());
        staticSubject.say("66666666");
        staticSubject.sleep();
    }
}
