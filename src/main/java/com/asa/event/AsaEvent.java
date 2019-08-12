package com.asa.event;

import org.springframework.context.ApplicationEvent;

/**
 * @Description
 * @Date 2019-05-31 9:33
 * @Author Asa
 * @Version 1.0
 **/

public class AsaEvent extends ApplicationEvent {

    private AsaEntity asaEntity;

    public AsaEvent(Object source, AsaEntity asaEntity) {
        super(source);
        this.asaEntity = asaEntity;
    }

    public AsaEntity getAsaEntity() {
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        return this.asaEntity;
    }
    /*public void setAsaEntity(AsaEntity asaEntity) {
        System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
        this.asaEntity = asaEntity;
    }*/

     public void getAsaEntitys() {
        System.out.println("ddddddddd");
    }

}
