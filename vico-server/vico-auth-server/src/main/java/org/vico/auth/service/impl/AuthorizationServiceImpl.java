package org.vico.auth.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.vico.auth.pojo.User;
import org.vico.auth.service.ImRelatedService;
import org.vico.auth.service.handler.SignBaseHandler;
import org.vico.auth.service.AuthorizationService;
import org.vico.auth.service.handler.SignInHandler;
import org.vico.auth.service.handler.SignUpHandler;
import org.vico.auth.utils.TokenUtil;
import org.vico.auth.utils.Transfer;
import org.vico.common.constant.StatusCode;

import lombok.val;
import org.vico.common.utils.CommonUtil;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Resource
    private List<SignUpHandler> signUpHandlerList;

    @Resource
    private List<SignInHandler> signInHandlerList;

    @Resource
    private TokenUtil tokenUtil;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private ImRelatedService imRelatedService;

    @Override
    public Transfer signIn(String type, User user) {
        val handler = findHandler(signInHandlerList, type);
        if(handler != null){
            Transfer res = handler.apply(user);
            if(res.status() == StatusCode.SUCCESS){
                // 负载均衡
                val serverInfo = imRelatedService.choose();
                if(serverInfo == null){
                    res.status(StatusCode.BL_NO_SERVER)
                            .clearParams()
                            .build();
                }else{
                    redisTemplate.opsForHash().put("ROUTING_KEYS", user.getId().toString(), serverInfo.getHost() + "#" + serverInfo.getPort());
                    res.param("host", serverInfo.getHost())
                        .param("port", serverInfo.getPort())
                        .param("token", tokenUtil.generate(user));
                }
            }
            return res;
        }
        return new Transfer(StatusCode.NO_HANDLER);
    }

    @Override
    public Transfer signUp(String type, User user) {
        val handler = findHandler(signUpHandlerList, type);
        if(handler != null){
            return handler.apply(user);
        }
        return new Transfer(StatusCode.NO_HANDLER);
    }

    @Override
    public Transfer logout(String userId) {
        redisTemplate.opsForHash().delete("TOKENS", userId);
        return new Transfer(StatusCode.SUCCESS);
    }

    @Override
    public boolean verifyToken(String token) {
        if(!CommonUtil.checkEmpty(token)){
            log.warn("TOKEN 为空");
            return false;
        }
        return tokenUtil.verify(token);
    }

    @Override
    public String generateToken(User user) {
        return tokenUtil.generate(user);
    }

    private SignBaseHandler findHandler(List<? extends SignBaseHandler> list, String type){
        for(SignBaseHandler handler : list){
            try{
                if(handler.hit(SignBaseHandler.SIGN_TYPE.valueOf(type.toUpperCase()))){
                    return handler;
                }
            }catch (IllegalArgumentException e){ }
        }
        return null;
    }
}
