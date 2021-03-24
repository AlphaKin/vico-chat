package org.vico.im.handler.processor;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.vico.common.utils.CommonUtil;
import org.vico.im.mapper.C2cMessageRecordMapper;
import org.vico.im.mapper.C2gMessageRecordMapper;
import org.vico.im.mapper.UserMapper;
import org.vico.im.pojo.MessageRecord;
import org.vico.im.proto.ProtoMessage.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.Future;

@Slf4j
@Component("MessageProcessor")
public class MessageProcessor extends BaseProcessor implements ImProcessor {

    @Resource
    private C2cMessageRecordMapper c2cMessageRecordMapper;

    @Resource
    private C2gMessageRecordMapper c2gMessageRecordMapper;

    @Resource
    private UserMapper userMapper;

    // 注册处理请求类型
    public MessageProcessor(){
        processorMap.put(CommandType.MESSAGE_TEXT_REQUEST, this);
    }

    @Override
    public Future<Object> execute(ChannelHandlerContext ctx, AggregatedMessage aggregatedMessage) {
        val message = aggregatedMessage.getTextMsgReq();

        // AES解密过程

        // ...
        val msg = toMessageRecord(message);

        // 持久化 并 进行转发处理
        boolean res = message.getIsGroup() ?
                C2gMessageDeal(ctx, aggregatedMessage, msg) :
                C2cMessageDeal(ctx, aggregatedMessage, msg);

        return new AsyncResult<>(true);
    }

    // 处理被转发消息
    @Override
    public void forwardDeal(AggregatedMessage message) {
        val textMsgReq = message.getTextMsgReq();
        val session = imSessionManager.getByUserId(textMsgReq.getTo());

        System.out.println("被转发的消息");

        //目标在线
        if(session != null){
            ByteBuf buff = Unpooled.buffer().writeBytes(
                    AggregatedMessage.newBuilder()
                            .setCommandType(CommandType.MESSAGE_TEXT_REQUEST)
                            .setCode(1)
                            .setTextMsgReq(textMsgReq)
                            .build()
                            .toByteArray()
            );
            session.writeAndFlush(new BinaryWebSocketFrame(buff));
        }else{
            //目标离线
            StringBuilder key = new StringBuilder();
            if(textMsgReq.getIsGroup()){
                key.append("g");
            }
            key.append(textMsgReq.getFrom())
                    .append(textMsgReq.getTo())
                    .append(textMsgReq.getTime());

            // 获取ContentId
            Integer contentId = (Integer) redisTemplate.opsForHash().get("TRANSIENT_CONTENT_IDS", key.toString());

            if(CommonUtil.checkEmpty(contentId)){
                val msg = toMessageRecord(textMsgReq);
                msg.setContentId(Long.valueOf(contentId));
                if(msg.getIsGroup()){
                    c2cMessageRecordMapper.insertSingleOfflineMessage(msg);
                }else{
                    c2gMessageRecordMapper.insertSingleOfflineMessage(msg);
                }
                redisTemplate.opsForHash().delete("TRANSIENT_CONTENT_IDS", key.toString());
            }
        }
    }

    // 单聊消息处理
    @Transactional
    public boolean C2cMessageDeal(ChannelHandlerContext ctx, AggregatedMessage request, MessageRecord messageRecord){
        // 持久化
        c2cMessageRecordMapper.insertSingleMessageContent(messageRecord);
        c2cMessageRecordMapper.insertSingleMessage(messageRecord);

        // 暂存 ContentID
        redisTemplate.opsForHash().put(
                "TRANSIENT_CONTENT_IDS",
                getContentCacheId(messageRecord, request.getTime()),
                messageRecord.getContentId()
        );

        // 根据routingKey转发
        MessageForward(request, String.valueOf(messageRecord.getToId()));
        return true;
    }

    // 群聊消息处理
    @Transactional
    public boolean C2gMessageDeal(ChannelHandlerContext ctx, AggregatedMessage aggregatedMessage, MessageRecord messageRecord){
        val request = aggregatedMessage.getTextMsgReq();

        // 持久化
        c2gMessageRecordMapper.insertSingleMessageContent(messageRecord);
        c2gMessageRecordMapper.insertSingleMessage(messageRecord);

        // 暂存 ContentID
        redisTemplate.opsForHash().put(
                "TRANSIENT_CONTENT_IDS",
                getContentCacheId(messageRecord, request.getTime()),
                messageRecord.getContentId()
        );

        // 获取群成员ID
        List<Long> userIds = userMapper.selectAllUserIdInGroup(messageRecord.getToId());
        if(userIds != null){
            userIds.forEach((userId) -> {
                if (!userId.equals(request.getFrom())) {
                    val message = AggregatedMessage.newBuilder()
                            .setCode(2)
                            .setCommandType(CommandType.MESSAGE_TEXT_REQUEST)
                            .setTextMsgReq(
                                    TextMessageRequest.newBuilder()
                                            .setContent(request.getContent())
                                            .setFrom(request.getFrom())
                                            .setTo(String.valueOf(userId))
                                            .setGroupId(request.getTo())
                                            .setIsGroup(request.getIsGroup())
                                            .setSessionId(request.getSessionId())
                                            .setTime(request.getTime())
                                            .build()
                            ).build();
                    MessageForward(message, String.valueOf(userId));
                }
            });
        }

        return true;
    }


    // 获取缓存中ContentID
    private String getContentCacheId(MessageRecord messageRecord, Long time){
        StringBuilder key = new StringBuilder();
        if(messageRecord.getIsGroup()){
            key.append("g");
        }
        key.append(messageRecord.getFromId())
           .append(messageRecord.getToId())
           .append(time);
        return key.toString();
    }


    // 转换为MessageRecord对象
    private MessageRecord toMessageRecord(TextMessageRequest message){
        val msg = new MessageRecord();
        msg.setFromId(Long.parseLong(message.getFrom()));
        if(message.getIsGroup() && message.getGroupId() != null && !message.getGroupId().equals("")){
            msg.setGroupId(Long.parseLong(message.getGroupId()));
        }
        msg.setToId(Long.parseLong(message.getTo()));
        msg.setMsgcSendtime(new Timestamp(message.getTime()));
        msg.setMsgcContent(message.getContent());
        msg.setMsgcStatus(1);
        msg.setIsGroup(message.getIsGroup());
        msg.setMtId(1);
        return msg;
    }
}