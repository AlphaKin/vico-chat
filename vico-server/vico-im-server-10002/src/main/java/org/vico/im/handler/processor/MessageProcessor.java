package org.vico.im.handler.processor;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.vico.im.core.ImSessionManager;
import org.vico.im.proto.ProtoMessage.*;
import org.vico.common.utils.CommonUtil;

import java.util.concurrent.Future;

@Slf4j
@Component("MessageProcessor")
public class MessageProcessor implements ImProcessor {
    @Override
    public Future<Object> execute(ChannelHandlerContext ctx, ImSessionManager imSessionManager, AggregatedMessage aggregatedMessage) {
        val message = aggregatedMessage.getTextMsg();

        if(CommonUtil.checkEmpty(aggregatedMessage.getSessionId())){
            log.error("SessionID cannot be empty");
            return new AsyncResult<>(false);
        }

        System.out.println(message.getFrom() + " -> " + message.getTo() + " : " + message.getContent());
        val response = AggregatedMessage.newBuilder()
                .setCommandType(CommandType.MESSAGE_TEXT_RESPONSE)
                .setToken("213435")
                .build();

        ByteBuf res = Unpooled.buffer().writeBytes(response.toByteArray());
        ctx.writeAndFlush(new BinaryWebSocketFrame(res));

        val session = imSessionManager.getBySessionId(aggregatedMessage.getSessionId());
        if(session == null){
            return new AsyncResult<>(false);
        }
        val aimSession = imSessionManager.getByUserId(session.getImUser().getUserId());



        aimSession.writeAndFlush(response.toByteArray());

        return new AsyncResult<>(true);
    }
}
