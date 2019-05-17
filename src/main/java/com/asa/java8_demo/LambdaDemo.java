package com.asa.java8_demo;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @className: LambdaDemo
 * @Description Lambda表达式案例
 * @Date 2018-09-21 16:00
 * @Author Asa
 * @Version 1.0
 **/
@Slf4j
public class LambdaDemo<T> {

    private T user;

    public Boolean test(Predicate<T> p, Predicate<T> o) {
        Predicate<T> b = p.and(o);
        return b.test(user);
    }

    public void setUser(T user) {
        this.user = user;
    }

    public T get(Supplier<T> supplier){
        return supplier.get();
    }

    public static void main(String[] args) {
        LambdaDemo<User> lambdaDemo = new LambdaDemo<>();
        lambdaDemo.setUser(new User(null, "asa", "123456"));
        boolean bool = lambdaDemo.test((user) -> {return null != user.getUsername();}, (user) -> {return null != user.getPassword();});
        User user = lambdaDemo.get(() -> {return new User(1L, "11", "22");});
        log.info(user.getUsername());
    }
}
