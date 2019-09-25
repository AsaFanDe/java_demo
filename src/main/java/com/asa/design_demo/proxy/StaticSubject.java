package com.asa.design_demo.proxy;

/**
 * @Description
 * @Date 2019-09-25 10:59
 * @Author Asa
 * @Version 1.0
 **/
public class StaticSubject implements Subject {

    Subject subject;

    public StaticSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public String say(String text) {
        System.out.println("-----------");
        subject.say(text);
        System.out.println("-----------");
        return text;
    }

    @Override
    public String sleep() {
        System.out.println("---------");
        subject.sleep();
        System.out.println("---------");
        return null;
    }
}
