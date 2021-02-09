package org.vico.im.core;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.vico.im.pojo.ImUser;

@Slf4j
@Data
@Builder
public class ImSession {
    public static final AttributeKey<ImSession> SESSION_KEY = AttributeKey.valueOf("sessionKey");
    private Channel channel;
    private String sessionId;
    private boolean isLoggedIn;
    private String userId;

    //获取 Session
    public static ImSession get(ChannelHandlerContext ctx){
        return ctx.channel().attr(ImSession.SESSION_KEY).get();
    }

    //关闭 Session
    public static void close(ChannelHandlerContext ctx){

    }

    //写入通道
    public synchronized void writeAndFlush(Object obj){
        this.channel.writeAndFlush(obj);
    }

    //绑定 Session
    public ImSession bind(){
        channel.attr(ImSession.SESSION_KEY).set(this);
        return this;
    }





    //关闭连接
//    public synchronized void close(){
//
//    }
}
