package com.asa.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @Description
 * @Date 2019-09-23 15:11
 * @Author Asa
 * @Version 1.0
 **/
public class NioClient {

    private String url;

    public NioClient(String url) {
        this.url = url;
    }

    public void work() throws IOException, URISyntaxException {
        SocketChannel sc = SocketChannel.open();
        Selector selector = Selector.open();

        sc.configureBlocking(false);
        URI uri = new URI(url);
        sc.connect(new InetSocketAddress(uri.getHost(), uri.getPort()));
        sc.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE);

        while (true) {
            if (selector.select() == 0) {
                continue;
            }
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                it.remove();

                if (key.isConnectable()) {
                    if (sc.isConnectionPending()) {
                        sc.finishConnect();
                    }
                }else if (key.isReadable()) {

                }
            }
        }
    }

    public static void main(String[] args) {
        int i = SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE;
        System.out.println(SelectionKey.OP_CONNECT & i);
        System.out.println(SelectionKey.OP_ACCEPT ^ i);
        System.out.println(1 << 2 & 6);
    }
}
