package com.asa.bio;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Created by AsaFan on 2018-09-01.
 */
@Slf4j
public class InetAddressTest {

    public static void main(String[] args) throws UnknownHostException {
        InetAddress address = InetAddress.getLocalHost();
        printInfo(address);
        log.info("-------------");
        InetAddress address1 = InetAddress.getByName("120.55.59.76");
        printInfo(address1);
    }

    private static void printInfo(InetAddress address) {
        log.info("计算机名：" + address.getHostName());
        log.info("IP地址：" + address.getHostAddress());
        byte[] addressB = address.getAddress();
        log.info("字节数组组成的IP: " + Arrays.toString(addressB));
        log.info(address.getCanonicalHostName());
    }
}
