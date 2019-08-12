package com.asa.reflection;

/**
 * @Description
 * @Date 2019-07-29 11:13
 * @Author Asa
 * @Version 1.0
 **/
public class AsaReflect {
    private String name;
    double money;
    protected int age;
    public boolean success;

    public AsaReflect(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public double getMoney() {
        return money;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setName(String name) {
        this.name = name;
    }
}
