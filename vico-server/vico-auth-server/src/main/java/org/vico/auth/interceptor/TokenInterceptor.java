package org.vico.auth.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.vico.auth.annotation.Token;
import org.vico.auth.utils.TokenUtil;
import org.vico.auth.utils.Transfer;
import org.vico.common.constant.StatusCode;
import org.vico.common.utils.CommonUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private TokenUtil tokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            Token annotation = ((HandlerMethod) handler).getMethodAnnotation(Token.class);
            if(annotation == null)
                return true;
            String token = request.getHeader("Authorization");
            boolean judge = CommonUtil.checkEmpty(token) && tokenUtil.verify(token.substring(7));
            if(!judge){
                response.setStatus(HttpStatus.SC_UNAUTHORIZED);
                response.getWriter().write(new Transfer(StatusCode.AUTH_NO_PERMISSION).build());
                return false;
            }
        }
        return true;
    }
}
