package com.asa.design_demo.proxy;

/**
 * @Description
 * @Date 2019-09-25 10:34
 * @Author Asa
 * @Version 1.0
 **/
public class DefaultSubject implements Subject {

    @Override
    public String say(String text) {
        System.out.println("default say");
        return text;
    }

    @Override
    public String sleep() {
        System.out.println("default sleep");
        return null;
    }
}
