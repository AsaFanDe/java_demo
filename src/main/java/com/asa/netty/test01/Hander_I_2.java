package com.asa.netty.test01;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Description
 * @Date 2019-09-04 14:04
 * @Author Asa
 * @Version 1.0
 **/
public class Hander_I_2 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Integer n = (Integer) msg;
        System.out.println("S in 2 get num = " + n.intValue());
        n++;
        ctx.channel().writeAndFlush(n);
    }
}
