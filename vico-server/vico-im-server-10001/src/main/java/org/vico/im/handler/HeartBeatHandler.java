package org.vico.im.handler;

import io.netty.channel.ChannelHandler;
import io.netty.handler.timeout.IdleState;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import org.springframework.stereotype.Component;

@Slf4j
@ChannelHandler.Sharable
@Component(value = "HeartBeatHandler")
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {
    public HeartBeatHandler(){

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            val idleEvent = (IdleStateEvent) evt;
            if(idleEvent.state() == IdleState.READER_IDLE){
            }else if(idleEvent.state() == IdleState.WRITER_IDLE){
            }else if (idleEvent.state() == IdleState.ALL_IDLE){
                ctx.channel().close();
                log.info(ctx.channel().localAddress() + " has Closed");
            }
        }
    }
}
