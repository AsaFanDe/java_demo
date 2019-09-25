package com.asa.design_demo.proxy;

import java.lang.reflect.Proxy;

/**
 * JDK动态代理
 * JDK动态代理只能对实现了接口的类生成代理，而不能针对类。
 * @Description
 * @Date 2019-09-25 10:32
 * @Author Asa
 * @Version 1.0
 **/
public class JDKProxyDemo {

    public static void main(String[] args) {
        Class[] classes = {Subject.class};
        ClassLoader classLoader = Subject.class.getClassLoader();
        Subject subject = (Subject) Proxy.newProxyInstance(classLoader, classes, new ProxySubject(new DefaultSubject()));
        subject.say("5555");
        subject.sleep();
    }
}
