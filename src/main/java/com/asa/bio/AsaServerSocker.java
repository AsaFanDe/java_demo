package com.asa.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by AsaFan on 2018-09-02.
 */
@Slf4j
public class AsaServerSocker {

    public static void main(String[] args){
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            log.info("服务器启动，等待客户端的链接");
            Socket socket = serverSocket.accept();
            //可以另外开启线程进行处理连接
            InputStream in = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
