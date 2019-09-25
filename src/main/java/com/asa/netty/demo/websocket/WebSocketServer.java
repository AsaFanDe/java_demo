package com.asa.netty.demo.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.ResourceLeakDetector;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by AsaFan on 2018-09-19.
 */
@Slf4j
public class WebSocketServer {

    private String port;

    EventLoopGroup boss;
    EventLoopGroup worker;

    public WebSocketServer(String port) {
        this.port = port;
    }

    // @PostConstruct
    public void run(){
        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup();
        try{
            ServerBootstrap server = new ServerBootstrap();
            ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.ADVANCED);
            server.group(boss, worker)
                  .channel(NioServerSocketChannel.class)
                  .option(ChannelOption.SO_BACKLOG, 1024*1024*10)
                  .option(ChannelOption.SO_REUSEADDR, true)
                  .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel)
                                throws Exception {
                            socketChannel.config()
                                    .setAllowHalfClosure(true);

                            ChannelPipeline p = socketChannel.pipeline();
                            p.addLast(new HttpServerCodec());
                            p.addLast(new HttpObjectAggregator(65536));
                            p.addLast(new ChunkedWriteHandler());
                            p.addLast(new CloseEvtHandler());
                            p.addLast("ws", new WebSocketHandler());
                        }
                        public LogLevel getLogLevel() { // 可以根据SpringBoot的LogLevel同步
                             return LogLevel.INFO;
                        }
                  });

            ChannelFuture channelFuture = server.bind(Integer.valueOf(port)).sync();
            if (channelFuture.isSuccess()) {
                log.info("【启动Netty服务成功，端口号：" + this.port + "】");
            }

            // 关闭连接
            // channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    // @PreDestroy
    public void shutdown() {
        boss.shutdownGracefully();
        worker.shutdownGracefully();
        log.error("关闭Netty服务");
    }

    public static void main(String[] args) {
        WebSocketServer server = new WebSocketServer("8073");
        server.run();
    }
}
