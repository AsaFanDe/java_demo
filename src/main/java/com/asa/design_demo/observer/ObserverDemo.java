package com.asa.design_demo.observer;

/**
 * 观察者模式
 * @Description
 * @Date 2019-09-25 11:45
 * @Author Asa
 * @Version 1.0
 **/
public class ObserverDemo {

    public static void main(String[] args){
        AsaObservable asa = new AsaObservable();
        AsaObserver arg0 = new AsaObserver("Asa");
        AsaObserver arg1 = new AsaObserver("Fan");
        asa.addObserver(arg0);
        asa.addObserver(arg1);
        asa.sendMsg("Hello");
    }
}
