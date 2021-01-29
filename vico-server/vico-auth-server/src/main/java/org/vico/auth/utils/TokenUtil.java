package org.vico.auth.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.vico.auth.pojo.User;
import org.vico.common.utils.CommonUtil;

import javax.annotation.Resource;

@Slf4j
@Component
public class TokenUtil {

    @Resource
    private RedisTemplate redisTemplate;

    @Value(value = "${auth.secret}")
    private String secret;

    public TokenUtil(){
        System.out.println("TOKEN UTIL ");
    }

    private static final int EXPIRE_TIME = 5 * 60 * 1000;

    public String generate(User user){
        return generate(user, true);
    }
    public String generate(User user, boolean isStateful){
        if(!CommonUtil.checkEmpty(user.getUserName()) || !CommonUtil.checkEmpty(user.getUserPassword())){
            throw new RuntimeException("无效参数");
        }
        String token = JWT.create()
                .withAudience(user.getUserName())
//                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .sign(Algorithm.HMAC256(secret));

        if(isStateful){
            redisTemplate.opsForHash().put("TOKENS", user.getUserName(), token);
        }
        return token;
    }

    public boolean remove(String token){
        return redisTemplate.opsForSet().remove("TOKENS", token) == 1;
    }

    public boolean verify(String token){
        return verify(token, true);
    }
    public boolean verify(String token, boolean isStateful){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
        try{
            String uid = verifier.verify(token).getAudience().get(0);
            if(isStateful){
                Object aimToken = redisTemplate.opsForHash().get("TOKENS", uid);
                return aimToken != null && token.equals(aimToken.toString());
            }
        }
        catch (JWTDecodeException | TokenExpiredException e){
            return false;
        }
        return true;
    }
}