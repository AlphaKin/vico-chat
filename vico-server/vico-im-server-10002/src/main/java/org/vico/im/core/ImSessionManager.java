package org.vico.im.core;

import io.netty.channel.Channel;
import lombok.Data;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.vico.common.pojo.ImServerMetaInfo;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Data
@Component("SessionManager")
public class ImSessionManager {
    private ConcurrentHashMap<String, ImSession> sessionMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, String> idMap = new ConcurrentHashMap<>();

    @Value("${auth.server-meta.prefix}")
    private String serverMetaPrefix;

    @Value("${spring.application.name}")
    private String instanceName;

    @Value("${server.host}")
    private String host;

    @Value("${server.port}")
    private Integer port;

    @Resource
    RedisTemplate redisTemplate;

    //添加 Session
    public void addSession(ImSession session){
        idMap.put(session.getImUser().getUserId(), session.getSessionId());
        sessionMap.put(session.getSessionId(), session);
        log.info("用户[id=" + session.getSessionId() + "] 登录");
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
        if(!idMap.containsKey(userId)){
            return null;
        }
        String sessionId = idMap.get(userId);
        if(sessionMap.containsKey(sessionId)){
            return sessionMap.get(sessionId);
        }
        return null;
    }

    //删除 Session
    public void remove(String sessionId){
        if(!sessionMap.containsKey(sessionId)){
            return;
        }
        ImSession session = sessionMap.get(sessionId);
        idMap.remove(session.getImUser().getUserId());
        sessionMap.remove(sessionId);
        log.info("用户[id=" + sessionId + "] 退出");
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
}
