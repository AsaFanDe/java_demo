package com.asa.netty.demo.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.ChannelInputShutdownEvent;
import io.netty.channel.socket.ChannelOutputShutdownEvent;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @Description
 * @Date 2019-09-05 9:28
 * @Author Asa
 * @Version 1.0
 **/
public class CloseEvtHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        try {
            Channel channel = ctx.channel();
            if (evt instanceof IdleStateEvent) {
                IdleStateEvent e = (IdleStateEvent) evt;
                if (e.state() == IdleState.READER_IDLE) {
                    channel.close();  //call back channelInactive(ChannelHandlerContext ctx)

                } //     because we are attaching  more importance to the read_idle time(timeout to rec)
                else if (e.state() == IdleState.WRITER_IDLE) { // No data was sent for a while.
                    channel.close();
                }
            } else if (evt instanceof ChannelInputShutdownEvent) {
                System.out.println("000000000000000000000");
            }else if (evt instanceof ChannelOutputShutdownEvent) {
                System.out.println("ppppppppppppppppppppppppppppp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
