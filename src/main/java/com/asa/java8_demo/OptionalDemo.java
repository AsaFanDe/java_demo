package com.asa.java8_demo;

import com.asa.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * @className: OptionalDemo
 * @Description Optional使用案例
 * @Date 2018-09-21 14:43
 * @Author Asa
 * @Version 1.0
 **/
@Slf4j
public class    OptionalDemo {

    public static void main(String[] args){
        OptionalDemo demo = new OptionalDemo();
        demo.mapTest();
    }

    /**
     * Optional如何处理返回为空的数据
     * @return
     */
    public String getUser(){
        String user = null;

        // 传统写法
        if (null == user){
            throw new BusinessException(401, "用户不存在");
        }

        // java8写法
        return Optional.ofNullable(user)
                .orElseThrow(() ->
                        new BusinessException(401, "用户不存在")
                );
    }

    /**
     * filter 方法的使用
     * @return
     */
    public User filterTest(){
        User user = new User(null, "Asa", "123456");

        // 传统写法
        if (null != user && null != user.getUsername()) {
            System.out.println(user.getUsername());
        }

        // filter写法
        Optional<User> optionalUser = Optional.of(user);
        optionalUser
                .filter(user1 -> null != user1.getUsername())
                .ifPresent((user1) -> {System.out.println(user1.getUsername());});

        return user;
    }

    public User orElseThrowTest() {
        User user = null;
        User user2 = new User(null, "Asa", "123456");

        Optional.ofNullable(user)
                .orElseThrow(() -> {return new ArithmeticException();});
        return user;
    }

    public User orElseGetTest() {
        User user = null;
        User user2 = new User(null, "Asa", "123456");

        return Optional.ofNullable(user)
                .orElseGet(() -> {return user2;});
    }

    public User flatMapTest() {
        User user = new User(null, "Asa", "123456");

       /* String username =  Optional.ofNullable(user)
                .flatMap(a -> Optional.ofNullable(a.getUsername().));*/
       return user;
    }

    public void mapTest(){
        Role role = new Role(null, "管理员");
        User user = new User(null, "Asa", "123456", role);

        User user0 = new User(null, "Asa", "123456");
        User user1 = null;

        Optional.ofNullable(user0)
                .map(u -> {return u.getRole();})
                .map(r -> {return r.getId();})
                .ifPresent(n -> {
                    System.out.println(n);
                });
    }

}
