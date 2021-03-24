package org.vico.im.handler.processor;

import lombok.val;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.vico.im.core.ImSessionManager;
import org.vico.im.proto.ProtoMessage;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Async
public abstract class BaseProcessor {

    @Resource
    protected ImSessionManager imSessionManager;

    @Resource
    protected RedisTemplate redisTemplate;

    protected Map<ProtoMessage.CommandType, ImProcessor> processorMap = new HashMap<>();

    // 转发消息到目标用户
    protected void MessageForward(ProtoMessage.AggregatedMessage request, String toId){
        String routingKey = (String) redisTemplate.opsForHash().get("ROUTING_KEYS", toId);
        if(routingKey != null){
            imSessionManager.forward(routingKey, ProtoMessage.AggregatedMessage.newBuilder(request).setIsForward(true).build());
        }
    }

    // 分发转发消息
    @RabbitListener(queues = "${auth.queue}")
    public void directReceiver(ProtoMessage.AggregatedMessage message){
        val processor = processorMap.get(message.getCommandType());
        System.out.println("分发消息[" + processorMap.size() + "]");
        if(processor != null){
            processor.forwardDeal(message);
        }
    }
}
