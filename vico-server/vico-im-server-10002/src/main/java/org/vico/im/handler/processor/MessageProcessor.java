package org.vico.im.handler.processor;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.vico.im.core.ImSessionManager;
import org.vico.im.proto.ProtoMessage.*;

import javax.annotation.Resource;
import java.util.concurrent.Future;

@Slf4j
@Component("MessageProcessor")
public class MessageProcessor implements ImProcessor {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private ImSessionManager imSessionManager;

    @Override
    public Future<Object> execute(ChannelHandlerContext ctx, AggregatedMessage aggregatedMessage) {
        val message = aggregatedMessage.getTextMsgReq();

        // AES解密过程
        
        // ...

        System.out.println(message.getFrom() + " -> " + message.getTo() + " : " + message.getContent());

        if (message.getIsGroup()) {
            System.out.println(message.getFrom() + " 在群里说: " + message.getContent());
            C2gMessageDeal(ctx, message);
        } else {
            C2cMessageDeal(ctx, message);
        }

        return new AsyncResult<>(true);
    }

    // 消息转发
    @RabbitListener(queues = "${auth.queue}")
    public void directReceiver(TextMessageRequest object){
        if(object.getIsGroup()){
            // 群聊
        }else{
            // 单聊
            val session = imSessionManager.getByUserId(object.getTo());
            val originSession = imSessionManager.getByUserId(object.getFrom());

            //目标在线
            if(session != null){
                val aggregatedMessage = AggregatedMessage.newBuilder()
                        .setCommandType(CommandType.MESSAGE_TEXT_REQUEST)
                        .setCode(1)
                        .setTextMsgReq(object)
                        .build();

                ByteBuf buff = Unpooled.buffer().writeBytes(aggregatedMessage.toByteArray());
                session.writeAndFlush(new BinaryWebSocketFrame(buff));
            }else{
                //目标离线
//            redisTemplate.opsForZSet().
            }
        }
    }

    public boolean C2cMessageDeal(ChannelHandlerContext ctx, TextMessageRequest request){
        // 单聊消息
        val session = imSessionManager.getByUserId(request.getTo());
//        if(session != null){
//            // 在本机上
//            ByteBuf res = Unpooled.buffer().writeBytes(aggregatedMessage.toByteArray());
//            session.writeAndFlush(new BinaryWebSocketFrame(res));
//        }else{
//            // 在其它机器上
//            String routingKey = (String) redisTemplate.opsForHash().get("ROUTING_KEYS", message.getTo());
//            imSessionManager.forward(routingKey, TextMessageRequest.newBuilder(message).setIsForward(true).build());
//        }
        String routingKey = (String) redisTemplate.opsForHash().get("ROUTING_KEYS", request.getTo());
        if(routingKey == null){
            return false;
        }
        imSessionManager.forward(routingKey, TextMessageRequest.newBuilder(request).setIsForward(true).build());

        return true;
    }

    public boolean C2gMessageDeal(ChannelHandlerContext ctx, TextMessageRequest request){
        return true;
    }
}