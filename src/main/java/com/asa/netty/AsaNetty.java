package com.asa.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;

/**
 * @Description
 * @Date 2019-09-04 14:01
 * @Author Asa
 * @Version 1.0
 **/
public class AsaNetty {

    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.buffer();
        PooledByteBufAllocator pool = new PooledByteBufAllocator(true);
        pool.ioBuffer();
        Unpooled.buffer();
    }
}
