package com.asa.java8_demo;

import lombok.Data;

/**
 * @className: User
 * @Description 用户使用案例
 * @Date 2018-09-21 15:10
 * @Author Asa
 * @Version 1.0
 **/

@Data
public class User {

    private Long id;

    private String username;

    private String password;

    public User() {
    }

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
