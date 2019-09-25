package com.asa.netty.test01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by AsaFan on 2018-09-19.
 */
@Slf4j
public class WebSocketServer {

    private int port;

    public WebSocketServer(int port) {
        this.port = port;
    }

    /**
     * 开启服务
     */
    public void run(){
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try{
            ServerBootstrap server = new ServerBootstrap();

            server.group(boss, worker)
                  .channel(NioServerSocketChannel.class)
                  .option(ChannelOption.SO_BACKLOG, 1024*1024*10)
                  .option(ChannelOption.SO_REUSEADDR, true)
                  .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel)
                                throws Exception {
                            socketChannel.config().setAllowHalfClosure(true);
                            ChannelPipeline p = socketChannel.pipeline();
                            p.addLast(new ObjectEncoder());
                            p.addLast(new ObjectDecoder(Integer.MAX_VALUE,
                                    ClassResolvers.weakCachingConcurrentResolver(null)));

                            p.addLast(new Hander_I_1());
                            p.addLast(new Hander_I_2());
                            p.addLast(new Hander_O_1());
                            p.addLast(new Hander_O_2());

                        }

                        public LogLevel getLogLevel() { // 可以根据SpringBoot的LogLevel同步
                             return LogLevel.INFO;
                        }
                  });

            ChannelFuture channelFuture = server.bind(port).sync();
            if (channelFuture.isSuccess()) {
                System.err.println("启动Netty服务成功，端口号：" + this.port);
            }

            // 关闭连接
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        WebSocketServer server = new WebSocketServer(6000);
        server.run();
    }

}
