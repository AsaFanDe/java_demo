package com.asa.design_demo.observer;

import java.util.Observable;

/**
 * Created by AsaFan on 2018-09-05.
 */
public class AsaObservable extends Observable {

    private String msg;

    public String getMsg(){
        return msg;
    }

    public void sendMsg(String msg) {
        this.msg = msg;
        setChanged();
        notifyObservers(this);
    }
}
