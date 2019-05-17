package com.asa.base;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

/**
 * @className: TransientTest
 * @Description //TODO
 * @Date 2018-09-25 10:32
 * @Author Asa
 * @Version 1.0
 **/

@RestController
public class TransientTest {

    @RequestMapping(value = "/transient")
    public Demo transientTest(){
        Demo demo = new Demo("Asa");
        System.out.print(demo.toString());
        return demo;
    }

    @RequestMapping(value = "/test")
    public String ttest(){
        return "1111";
    }

    @Data
    public static class Demo implements Serializable{
        private static final long serialVersionUID = -2134842711216889282L;
        private int id;


        private transient String name;

        public Demo() {
            this.id = 1;
        }

        public Demo(String name) {
            this();
            this.name = name;
        }
    }
}
