package com.asa.redis;

import redis.clients.jedis.Jedis;

/**
 * @Description redis分布式锁案例
 * @Date 2019-09-26 9:13
 * @Author Asa
 * @Version 1.0
 **/
public class DistributedLockDemo {

    public static void main(String[] args) throws InterruptedException {
        Jedis jedis = new Jedis("localhost", 6379);
        System.out.println(jedis.setnx("asa","5555"));
        jedis.expire("asa", 30);
        Thread.sleep(31000);
        System.out.println(jedis.setnx("asa", "6666"));
    }
}
