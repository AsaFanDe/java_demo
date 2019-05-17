package com.asa.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @className: UserServiceTest
 * @Description //TODO
 * @Date 2018-09-26 11:44
 * @Author Asa
 * @Version 1.0
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void usetTest(){
        userService.test();
    }
}
