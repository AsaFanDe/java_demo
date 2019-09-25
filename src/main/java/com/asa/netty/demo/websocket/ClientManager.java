package com.asa.netty.demo.websocket;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 客户端连接管理
 * @Description
 * @Date 2019-08-21 14:35
 * @Author Asa
 * @Version 1.0
 **/
@Slf4j
public final class ClientManager {

    private static final Map<String, Channel> clients = new ConcurrentHashMap();

    public static final String deviceId = "deviceid";

    public static void add(String clientId, Channel channel) {
        Optional.ofNullable(clients.get(clientId))
                .ifPresent(channel1 -> {
                    channel1.close();
                });

        channel.attr(AttributeKey.valueOf(deviceId)).set(clientId);
        clients.put(clientId, channel);
        log.info("{}上线了，在线客户端：{}", clientId, clients.size());
    }

    public static Channel get(String clientId) {
        return clients.get(clientId);
    }

    public static Map<String, Channel> getAll() {
        return clients;
    }

    /**
     * 推送
     * @param clientId
     * @param msg
     */
    public static void push(String clientId, String msg) {
        Channel channel = get(clientId);
        if (null != channel) {
            if (!channel.writeAndFlush(new TextWebSocketFrame(msg)).isDone()) {
                /*channel.close(); // 关闭通道
                clients.remove(clientId);*/
                // log.error("消息推送失败：{} ", msg);
            }
        }else {
            log.info("{}通道不存在", clientId);
        }
    }

    public static void close(Channel channel) {
        channel.close();
        Object deviceid = channel.attr(AttributeKey.valueOf(deviceId)).get();
        if (null != deviceid) {
            clients.remove(deviceid);
        }
        log.info("{}下线了，在线客户端：{}", deviceid, clients.size());
    }
}
