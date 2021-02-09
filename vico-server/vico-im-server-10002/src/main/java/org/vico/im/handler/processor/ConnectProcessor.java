package org.vico.im.handler.processor;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.vico.im.core.ImSession;
import org.vico.im.core.ImSessionManager;
import org.vico.im.pojo.ImUser;
import org.vico.im.proto.ProtoMessage;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.Future;

@Slf4j
@Component("ConnectProcessor")
public class ConnectProcessor implements ImProcessor{

    @Resource
    private ImSessionManager imSessionManager;

    @Override
    public Future<Object> execute(ChannelHandlerContext ctx, ProtoMessage.AggregatedMessage aggregatedMessage) {
        val message = aggregatedMessage.getConnectReq();

        // 构造Session
        String sessionId = UUID.randomUUID().toString();
        val newSession = ImSession.builder()
                .channel(ctx.channel())
                .sessionId(sessionId)
                .userId(message.getUserId())
                .build();
        newSession.bind();
        imSessionManager.addSession(newSession);
        log.info("[" + newSession.getUserId() + "] connect");
        log.info("当前Session数: " + imSessionManager.getSessionMap().size());

        //更新服务信息缓存
        imSessionManager.updateServerMeta();

        // proto响应
        val connectResponse = ProtoMessage.ConnectResponse.newBuilder()
                .setSessionId(sessionId)
                .setKey("secret key")
                .build();
        val response = ProtoMessage.AggregatedMessage.newBuilder()
                .setCommandType(ProtoMessage.CommandType.CONNECT_RESPONSE)
                .setCode(1)
                .setConnectResp(connectResponse)
                .build();

        ByteBuf res = Unpooled.buffer().writeBytes(response.toByteArray());
        newSession.writeAndFlush(new BinaryWebSocketFrame(res));
        return new AsyncResult<>(true);
    }
}
