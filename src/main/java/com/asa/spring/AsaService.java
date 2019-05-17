package com.asa.spring;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @className: Service
 * @Description //TODO
 * @Date 2018-09-26 11:33
 * @Author Asa
 * @Version 1.0
 **/

@Data
@Service
public class AsaService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


}
