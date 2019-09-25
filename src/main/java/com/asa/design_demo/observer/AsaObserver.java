package com.asa.design_demo.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by AsaFan on 2018-09-05.
 */
public class AsaObserver implements Observer{

    public String name;

    public AsaObserver(String name) {
        this.name = name;
    }

    @Override
    public void update(Observable o, Object arg) {
        AsaObservable a = (AsaObservable) o;
        System.out.println(name + "-" +a.getMsg());
    }

    public static void main(String[] args){
        AsaObservable asa = new AsaObservable();
        AsaObserver arg0 = new AsaObserver("Asa");
        AsaObserver arg1 = new AsaObserver("Fan");
        asa.addObserver(arg0);
        asa.addObserver(arg1);
        asa.sendMsg("Hello");
    }
}
