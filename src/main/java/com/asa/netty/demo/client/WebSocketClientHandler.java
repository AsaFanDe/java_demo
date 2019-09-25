package com.asa.netty.demo.client;

import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Date 2019-08-14 17:49
 * @Author Asa
 * @Version 1.0
 **/
@Slf4j
public class WebSocketClientHandler extends ChannelInboundHandlerAdapter {

    WebSocketClientHandshaker handshaker;
    ChannelPromise handshakeFuture;

    public void handlerAdded(ChannelHandlerContext ctx) {
        this.handshakeFuture = ctx.newPromise();
    }
    public WebSocketClientHandshaker getHandshaker() {
        return handshaker;
    }

    public void setHandshaker(WebSocketClientHandshaker handshaker) {
        this.handshaker = handshaker;
    }

    public ChannelPromise getHandshakeFuture() {
        return handshakeFuture;
    }

    public void setHandshakeFuture(ChannelPromise handshakeFuture) {
        this.handshakeFuture = handshakeFuture;
    }

    public ChannelFuture handshakeFuture() {
        return this.handshakeFuture;
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // System.out.println("channelRead  " + this.handshaker.isHandshakeComplete());
        Channel ch = ctx.channel();
        FullHttpResponse response;
        log.info("888888888888888888888888888");
        if (!this.handshaker.isHandshakeComplete()) {
            try {
                response = (FullHttpResponse) msg;
                //握手协议返回，设置结束握手
                this.handshaker.finishHandshake(ch, response);
                //设置成功
                this.handshakeFuture.setSuccess();
                log.info("握手成功");
                // System.out.println("WebSocket Client connected! response headers[sec-websocket-extensions]:{}" + response.headers());
            } catch (WebSocketHandshakeException var7) {
                FullHttpResponse res = (FullHttpResponse) msg;
                String errorMsg = String.format("WebSocket Client failed to connect,status:%s,reason:%s", res.status(), res.content().toString(CharsetUtil.UTF_8));
                this.handshakeFuture.setFailure(new Exception(errorMsg));
            }
        } else if (msg instanceof FullHttpResponse) {
            response = (FullHttpResponse) msg;
            //this.listener.onFail(response.status().code(), response.content().toString(CharsetUtil.UTF_8));
            throw new IllegalStateException("Unexpected FullHttpResponse (getStatus=" + response.status() + ", content=" + response.content().toString(CharsetUtil.UTF_8) + ')');
        } else if (msg instanceof WebSocketFrame){
            WebSocketFrame frame = (WebSocketFrame) msg;
            if (frame instanceof TextWebSocketFrame) {
                TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
                log.info(textFrame.text());
                //this.listener.onMessage(textFrame.text());
                System.out.println("TextWebSocketFrame");
            } else if (frame instanceof BinaryWebSocketFrame) {
                System.out.println("BinaryWebSocketFrame");
            } else if (frame instanceof PongWebSocketFrame) {
                // 记录更新pong的时间，如果pong的时间长久未更新，则表示以断开
                System.out.println("WebSocket Client received pong");
            } else if (frame instanceof CloseWebSocketFrame) {
                System.out.println("receive close frame");
                //this.listener.onClose(((CloseWebSocketFrame)frame).statusCode(), ((CloseWebSocketFrame)frame).reasonText());
                ch.close();
            }

        }else {
            System.out.println("msg type unable receive");
        }
    }

}
