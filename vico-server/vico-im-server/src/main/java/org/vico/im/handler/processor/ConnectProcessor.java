package org.vico.im.handler.processor;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.vico.im.core.ImSession;
import org.vico.im.core.SessionManager;
import org.vico.im.pojo.ImUser;
import org.vico.im.proto.ProtoMessage;

import java.util.UUID;
import java.util.concurrent.Future;

@Slf4j
@Component("ConnectProcessor")
public class ConnectProcessor implements ImProcessor{
    @Override
    public Future<Object> execute(ChannelHandlerContext ctx, SessionManager sessionManager, ProtoMessage.AggregatedMessage aggregatedMessage) {
        val message = aggregatedMessage.getConnectMsg();
        val newSession = ImSession.builder()
                .channel(ctx.channel())
                .sessionId(UUID.randomUUID().toString())
                .imUser(new ImUser(message.getUserId(), "testName", 18))
                .build();
        newSession.bind();
        sessionManager.addSession(newSession);

        log.info("[" + newSession.getImUser().getUserId() + "] connect");
        log.info("当前Session数: " + sessionManager.getSessionMap().size());

        val commonResponse = ProtoMessage.CommonResponse.newBuilder()
                .setCode(200)
                .build();
        val response = ProtoMessage.AggregatedMessage.newBuilder()
                .setCommandType(ProtoMessage.CommandType.CONNECT_RESPONSE)
                .setCommonRes(commonResponse)
                .build();

        ByteBuf res = Unpooled.buffer().writeBytes(response.toByteArray());
        newSession.writeAndFlush(new BinaryWebSocketFrame(res));
        return new AsyncResult<>(true);
    }
}
