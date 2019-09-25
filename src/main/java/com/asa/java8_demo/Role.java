package com.asa.java8_demo;

import lombok.Data;

/**
 * @Description
 * @Date 2019-09-19 15:54
 * @Author Asa
 * @Version 1.0
 **/
@Data
public class Role {

    String roleName;
    String id;

    public Role(String roleName, String id) {
        this.roleName = roleName;
        this.id = id;
    }
}
