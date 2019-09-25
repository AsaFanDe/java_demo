package com.asa.netty.test01;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Description
 * @Date 2019-09-04 14:12
 * @Author Asa
 * @Version 1.0
 **/
public class ClientHander extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Integer n = (Integer) msg;
        System.out.println("S out 1 get num = " + n.intValue());
        // ctx.writeAndFlush(n);
    }
}
