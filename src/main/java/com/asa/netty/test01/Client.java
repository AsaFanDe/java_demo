package com.asa.netty.test01;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.Data;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @Description
 * @Date 2019-09-04 14:12
 * @Author Asa
 * @Version 1.0
 **/
@Data
public class Client {
    Channel channel;

    public void bind(String url) throws InterruptedException, URISyntaxException {
        EventLoopGroup group = new NioEventLoopGroup(1);
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_BACKLOG, 1024*1024*10)
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline p = socketChannel.pipeline();
                        p.addLast(new ObjectEncoder());
                        p.addLast(new ObjectDecoder(Integer.MAX_VALUE,
                                ClassResolvers.weakCachingConcurrentResolver(null))); // 最大长度
                        p.addLast(new ClientHander());
                    }
                });

        URI uri = new URI(url);
        ChannelFuture future = bootstrap.connect(uri.getHost(), uri.getPort()).sync();
        if (future.isSuccess()) {
            channel = future.channel();
        }
    }

    public static void main(String[] args) throws InterruptedException, URISyntaxException {
        Client client = new Client();
        client.bind("ws://127.0.0.1:6000");
        System.out.println("===========================");
        if (client.getChannel().writeAndFlush(1).isSuccess()) {
            System.out.println("5555555555555555555");
        }
    }
}
