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
import org.vico.im.mapper.C2cMessageRecordMapper;
import org.vico.im.mapper.C2gMessageRecordMapper;
import org.vico.im.pojo.MessageRecord;
import org.vico.im.proto.ProtoMessage;
import org.vico.im.proto.ProtoMessage.CommandType;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;

@Slf4j
@Component("ConnectProcessor")
public class ConnectProcessor extends BaseProcessor implements ImProcessor{

    @Resource
    private C2cMessageRecordMapper c2cMapper;

    @Resource
    private C2gMessageRecordMapper c2gMapper;

    // 注册处理请求类型
    public ConnectProcessor(){
        processorMap.put(CommandType.CONNECT_REQUEST, this);
    }

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
        val response = ProtoMessage.AggregatedMessage.newBuilder()
                .setCommandType(ProtoMessage.CommandType.CONNECT_RESPONSE)
                .setCode(1)
                .setConnectResp(
                        ProtoMessage.ConnectResponse.newBuilder()
                                .setSessionId(sessionId)
                                .setKey("secret key")
                                .build()
                ).build();

        ByteBuf res = Unpooled.buffer().writeBytes(response.toByteArray());
        newSession.writeAndFlush(new BinaryWebSocketFrame(res));

//        sendOfflineMessages(Long.parseLong(message.getUserId()), newSession);
        sendRoamMessages(Long.parseLong(message.getUserId()), newSession);

        return new AsyncResult<>(true);
    }


    // 对连接请求无用
    @Override
    public void forwardDeal(ProtoMessage.AggregatedMessage message) {

    }

    // 推送离线消息
    private void sendOfflineMessages(Long id, ImSession session){
        List<MessageRecord> offlineMessages = c2cMapper.selectOfflineMessage(id);
        offlineMessages.forEach((item) -> {
            val msg = ProtoMessage.AggregatedMessage.newBuilder()
                    .setCommandType(ProtoMessage.CommandType.MESSAGE_TEXT_REQUEST)
                    .setCode(1)
                    .setTextMsgReq(
                            ProtoMessage.TextMessageRequest.newBuilder()
                                    .setContent(item.getMsgcContent())
                                    .setFrom(String.valueOf(item.getFromId()))
                                    .setTo(String.valueOf(item.getToId()))
                                    .setTime(item.getMsgcSendtime().getTime())
                                    .setIsRoamed(true)
                                    .build()
                    ).build();

            ByteBuf res = Unpooled.buffer().writeBytes(msg.toByteArray());
            session.writeAndFlush(new BinaryWebSocketFrame(res));
        });
        c2cMapper.deleteOfflineMessage(id);
    }

    // 消息漫游
    private void sendRoamMessages(Long userId, ImSession session){
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH) - 7,0,0,0);
        Long time = calendar.getTimeInMillis();

        List<MessageRecord> c2cRoamMessages = c2cMapper.selectRoamMessages(userId, time);
        c2cRoamMessages.forEach((item) -> {
            val msg = ProtoMessage.AggregatedMessage.newBuilder()
                    .setCommandType(ProtoMessage.CommandType.MESSAGE_TEXT_REQUEST)
                    .setCode(1)
                    .setTextMsgReq(
                            ProtoMessage.TextMessageRequest.newBuilder()
                                    .setContent(item.getMsgcContent())
                                    .setFrom(String.valueOf(item.getFromId()))
                                    .setTo(String.valueOf(item.getToId()))
                                    .setTime(item.getMsgcSendtime().getTime())
                                    .setIsRoamed(true)
                                    .build()
                    ).build();

            ByteBuf res = Unpooled.buffer().writeBytes(msg.toByteArray());
            session.writeAndFlush(new BinaryWebSocketFrame(res));
        });


        List<MessageRecord> c2gRoamMessages = c2gMapper.selectRoamMessages(userId, time);
        c2gRoamMessages.forEach((item) -> {
            val msg = ProtoMessage.AggregatedMessage.newBuilder()
                    .setCommandType(ProtoMessage.CommandType.MESSAGE_TEXT_REQUEST)
                    .setCode(1)
                    .setTextMsgReq(
                            ProtoMessage.TextMessageRequest.newBuilder()
                                    .setContent(item.getMsgcContent())
                                    .setFrom(String.valueOf(item.getFromId()))
                                    .setTo(String.valueOf(userId))
                                    .setGroupId(String.valueOf(item.getToId()))
                                    .setTime(item.getMsgcSendtime().getTime())
                                    .setIsGroup(true)
                                    .setIsRoamed(true)
                                    .build()
                    ).build();

            ByteBuf res = Unpooled.buffer().writeBytes(msg.toByteArray());
            session.writeAndFlush(new BinaryWebSocketFrame(res));
        });
    }
}
