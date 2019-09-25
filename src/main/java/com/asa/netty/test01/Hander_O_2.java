package com.asa.netty.test01;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @Description
 * @Date 2019-09-04 14:05
 * @Author Asa
 * @Version 1.0
 **/
public class Hander_O_2 extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        Integer n = (Integer) msg;
        System.out.println("S out 2 get num = " + n.intValue());
        n++;
        ctx.writeAndFlush(n);
    }
}
