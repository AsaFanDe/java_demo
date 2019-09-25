package com.asa.design_demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Description
 * @Date 2019-09-25 10:37
 * @Author Asa
 * @Version 1.0
 **/
public class ProxySubject implements InvocationHandler {

    private Object obj;

    public ProxySubject(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("-------------------");
        Object result = method.invoke(obj, args);
        System.out.println("-------------------");
        return result;
    }
}
