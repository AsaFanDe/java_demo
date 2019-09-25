package com.asa.netty.demo.client;

import com.asa.netty.demo.websocket.CloseEvtHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * @Description
 * @Date 2019-08-14 17:47
 * @Author Asa
 * @Version 1.0
 **/
@Slf4j
public class NettyClient {

    String url; // 需要连接的url
    HttpHeaders httpHeaders; // 自定义headers信息 todo 带token来握手
    Channel channel; // 连接通道
    String subprotocol; // websocket 子协议
    Date pongTime; // 最近一次pong的状态

    static final String WEBSOCK_HANDLER = "WebSocketClientHandler";

    public NettyClient(String url) {
        if (null == url || url.trim().equals("")) {
            throw new NullPointerException("Websocket url can not null");
        }
        this.url = url;
    }

    public NettyClient(String url, HttpHeaders httpHeaders) {
        this(url);
        this.httpHeaders = httpHeaders;
    }

    public NettyClient(String url, HttpHeaders httpHeaders, String subprotocol) {
        this(url, httpHeaders);
        this.subprotocol = subprotocol;
    }

    public void bind() throws InterruptedException, URISyntaxException {
        EventLoopGroup group=new NioEventLoopGroup();
        Bootstrap boot=new Bootstrap();
        boot.option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true)
                .option(ChannelOption.SO_BACKLOG, 1024*1024*10)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000) // 连接超时设置
                .group(group)
                .handler(new LoggingHandler(LogLevel.INFO))
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.config().setAllowHalfClosure(true);
                        ChannelPipeline p = socketChannel.pipeline();
                        p.addLast(new HttpClientCodec());
                        p.addLast(new HttpObjectAggregator(1024*1024*10));
                        p.addLast(new CloseEvtHandler());
                        p.addLast(WEBSOCK_HANDLER, new WebSocketClientHandler());
                    }
                });
        URI websocketURI = new URI(url);
        log.info(url);
        ChannelFuture future = boot.connect(websocketURI.getHost(), websocketURI.getPort()).sync();
        if (future.isSuccess()) {
            channel = future.channel();
        }

        // 开始握手
        WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory
                .newHandshaker(websocketURI, WebSocketVersion.V13, subprotocol, true, httpHeaders);
        WebSocketClientHandler handler = (WebSocketClientHandler)channel.pipeline().get(WEBSOCK_HANDLER);
        handler.setHandshaker(handshaker);
        if (handshaker.handshake(channel).isSuccess()) {
            log.info("握手成功");
        }
        // 阻塞等待是否握手成功
        handler.handshakeFuture().sync();
    }


    public Channel getChannel() {
        return channel;
    }

    private static final String onlineUrl = "ws://dev.arounddoctor.com:8073/ws";
    private static final String localUrl = "ws://localhost:8073/ws";

    public static void main(String[] args) throws URISyntaxException, InterruptedException, UnknownHostException {
        HttpHeaders headers = new DefaultHttpHeaders();
        headers.add("Authorization", "Basic YWRtaW46d2FkYXRh");
        headers.add("x-deviceid", "123459");
        NettyClient client = new NettyClient(onlineUrl, headers);
        client.bind();

        client.channel.writeAndFlush(new PingWebSocketFrame());

        /*for (;;) {
            Thread.sleep(100);
            *//*log.info("55555555555");
            TextWebSocketFrame frame = new TextWebSocketFrame("555555555");
            if (client.getChannel().writeAndFlush(frame).isSuccess()) {
                log.info("发出成功");
            }*//*
            if (!client.getChannel().writeAndFlush(new PingWebSocketFrame()).isSuccess()) {
                log.info("ping error"); // todo ping失败进行重连
            }
        }*/
        /*PingWebSocketFrame ping = new PingWebSocketFrame();

        URI websocketURI = new URI("http://dev.arounddoctor.com");
        log.info(websocketURI.getHost());
        log.info(websocketURI.getPort() + "");*/
        // System.out.println(InetAddress.getByName("www.baidu.com"));
    }
}
