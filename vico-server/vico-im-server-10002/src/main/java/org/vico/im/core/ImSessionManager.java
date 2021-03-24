package org.vico.im.core;

import io.netty.channel.Channel;
import lombok.Data;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.vico.common.pojo.ImServerMetaInfo;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
@Data
@Component("SessionManager")
public class ImSessionManager {
    private Map<String, ImSession> sessionMap = new ConcurrentHashMap<>();
    private Map<String, String> idMap = new ConcurrentHashMap<>();
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
    private Lock writeLock = readWriteLock.writeLock();
    private Lock readLock = readWriteLock.readLock();

    @Value("${auth.server-meta.prefix}")
    private String serverMetaPrefix;

    @Value("${spring.application.name}")
    private String instanceName;

    @Value("${server.host}")
    private String host;

    @Value("${server.port}")
    private Integer port;

    @Value("${auth.exchange}")
    private String exchange;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    RedisTemplate redisTemplate;

    //添加 Session
    public void addSession(ImSession session){
        writeLock.lock();
        idMap.put(session.getUserId(), session.getSessionId());
        sessionMap.put(session.getSessionId(), session);
        writeLock.unlock();
        log.info("用户[id=" + session.getUserId() + "] 登录 | " + sessionMap.size());
    }

    //获取 Session
    public ImSession getBySessionId(String sessionId){
        if(sessionMap.containsKey(sessionId)){
            return sessionMap.get(sessionId);
        }
        return null;
    }

    //获取 Session
    public ImSession getByUserId(String userId){
        readLock.lock();
        if(!idMap.containsKey(userId)){
            return null;
        }
        String sessionId = idMap.get(userId);
        if(sessionMap.containsKey(sessionId)){
            return sessionMap.get(sessionId);
        }
        readLock.unlock();
        return null;
    }

    //删除 Session
    public void remove(String sessionId){
        if(!sessionMap.containsKey(sessionId)){
            return;
        }
        ImSession session = sessionMap.get(sessionId);

        writeLock.lock();
        redisTemplate.opsForHash().delete("ROUTING_KEYS", session.getUserId());
        redisTemplate.opsForHash().delete("TOKENS", session.getUserId());
        idMap.remove(session.getUserId());
        sessionMap.remove(sessionId);
        writeLock.unlock();

        log.info("用户[id=" + sessionId + "] 退出 | " + sessionMap.size());
    }

    public void remove(Channel channel){
        val session = channel.attr(ImSession.SESSION_KEY).get();
        if(session != null){
            remove(session.getSessionId());
            updateServerMeta();
        }
    }

    // 更新ServerMeta
    public void updateServerMeta(){
        String key = instanceName + ":" + port;
        redisTemplate.opsForHash().put(serverMetaPrefix, key, new ImServerMetaInfo(host, port, sessionMap.size()));
    }

    // 转发消息
    public void forward(String routingKey, Object object){
        rabbitTemplate.convertAndSend(exchange, routingKey, object);
    }
}
