package com.asa.netty.demo.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * @Description
 * @Date 2019-08-16 9:14
 * @Author Asa
 * @Version 1.0
 **/
@Slf4j
public class WebSocketHandler extends ChannelInboundHandlerAdapter {

    private WebSocketServerHandshaker handshaker;

    static final String Authorization = "Authorization";
    static final String Deviceid = "x-deviceid";

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) { // HTTP接入，WebSocket第一次连接使用HTTP连接,用于握手
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        }else if (msg instanceof WebSocketFrame) { // Websocket消息接入
            handlerWebSocketFrame(ctx, (WebSocketFrame) msg);
        }else {
            log.info("message {} not compliance with the transport protocol", msg);
        }
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        HttpHeaders headers = req.headers();
        if (!req.decoderResult().isSuccess()
                || (!"websocket".equals(headers.get("Upgrade")))) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }

        if (!this.auth(headers.get(Authorization))) { // 鉴权
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1, HttpResponseStatus.UNAUTHORIZED));
            return;
        }

        String deviceid = headers.get(Deviceid);
        if (StringUtils.isEmpty(deviceid)) { // 获取客户唯一id
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }

        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                "ws://"+req.headers().get(HttpHeaderNames.HOST) + req.uri(), null, false);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory
                    .sendUnsupportedVersionResponse(ctx.channel());
        } else {
            if (handshaker.handshake(ctx.channel(), req).isSuccess()){
                ClientManager.add(deviceid, ctx.channel());// 握手成功缓存客户的Channel
            }else {
                log.error("{}握手失败", deviceid);
            }
        }
    }

    /**
     * 鉴权
     * @param authorization 鉴权信息
     * @return
     */
    private boolean auth(String authorization) {
        if (StringUtils.isEmpty(authorization)) {
            log.info("Authorization can not be empty!");
            return false;
        }
        if (!authorization.equals("Basic YWRtaW46d2FkYXRh")){
            return false;
        }
        return true;
    }

    /**
     * 客户端传入的WebSocketFrame处理
     * @param ctx   handler上下文
     * @param frame 数据帧
     */
    private void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        if (frame instanceof CloseWebSocketFrame) { // 链路关闭指令
            ClientManager.close(ctx.channel());
        }else if(frame instanceof PingWebSocketFrame) { // 心跳指令
            ctx.writeAndFlush(new PongWebSocketFrame());
        }else if (frame instanceof BinaryWebSocketFrame) { // 二进制消息指令

        }else if (frame instanceof TextWebSocketFrame) { // 文本消息指令
            log.info(((TextWebSocketFrame) frame).text());
        }
    }


    /**
     * 发送响应信息
     * @param ctx
     * @param req
     * @param res
     */
    private void sendHttpResponse(ChannelHandlerContext ctx,
                                  FullHttpRequest req,
                                  DefaultFullHttpResponse res) {
        // 返回应答给客户端
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(),
                    CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }
        // 如果是非Keep-Alive，关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private boolean isKeepAlive(FullHttpRequest req) {
        return false;
    }
}
