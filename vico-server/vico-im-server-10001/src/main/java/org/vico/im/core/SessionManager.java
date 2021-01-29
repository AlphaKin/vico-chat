package org.vico.im.core;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Data
@Component("SessionManager")
public class SessionManager {
    private ConcurrentHashMap<String, ImSession> sessionMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, String> idMap = new ConcurrentHashMap<>();

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
}
