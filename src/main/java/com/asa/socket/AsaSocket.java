package com.asa.socket;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by AsaFan on 2018-09-02.
 */
public class AsaSocket {

    public static void main(String[] args){
        try {
            Socket socket = new Socket("localhost", 8888);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ThreadLocalExample a = new ThreadLocalExample();
        a.nextSeq();
    }
}
