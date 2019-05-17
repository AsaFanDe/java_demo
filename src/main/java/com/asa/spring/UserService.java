package com.asa.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @className: UserService
 * @Description //TODO
 * @Date 2018-09-26 11:34
 * @Author Asa
 * @Version 1.0
 **/

@Slf4j
@Service
public class UserService {

    @Autowired
    AsaService asaService;


    public void test(){
        System.out.println("user test");
        asaService.getRoleService().roleTest();
    }




}
