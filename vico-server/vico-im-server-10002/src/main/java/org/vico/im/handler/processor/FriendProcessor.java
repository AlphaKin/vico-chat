package org.vico.im.handler.processor;


import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.vico.im.core.ImSessionManager;
import org.vico.im.proto.ProtoMessage;
import org.vico.im.proto.ProtoMessage.CommandType;
import org.vico.im.proto.ProtoMessage.AggregatedMessage;

import javax.annotation.Resource;
import java.util.concurrent.Future;

@Slf4j
@Component("FriendProcessor")
public class FriendProcessor extends BaseProcessor implements ImProcessor{

    @Resource
    private ImSessionManager imSessionManager;

    // 注册处理请求类型
    public FriendProcessor(){
        processorMap.put(CommandType.FRIEND_REQUEST, this);
    }

    @Override
    public Future<Object> execute(ChannelHandlerContext ctx, AggregatedMessage aggregatedMessage) {
        val message = aggregatedMessage.getFriendReq();

        //  持久化
        // coding...

        // 转发消息
        MessageForward(aggregatedMessage, message.getTo());
        return new AsyncResult<>(true);
    }

    // 处理被转发好友请求
    @Override
    public void forwardDeal(AggregatedMessage message) {
        val friendReq = message.getFriendReq();
        System.out.println("被转发的好友请求");
        System.out.println(friendReq);
    }
}
