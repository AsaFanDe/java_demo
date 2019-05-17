package com.asa.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @className: RoleService
 * @Description //TODO
 * @Date 2018-09-26 11:37
 * @Author Asa
 * @Version 1.0
 **/

@Slf4j
@Service
public class RoleService {

    @Autowired
    AsaService asaService;

    public void roleTest(){
        System.out.println("role test");
    }
}
