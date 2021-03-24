package org.vico.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.vico.auth.annotation.Token;
import org.vico.auth.pojo.User;
import org.vico.auth.service.AuthorizationService;
import org.vico.auth.utils.Transfer;
import org.vico.common.constant.StatusCode;

import javax.annotation.Resource;

@Slf4j
@RestController
public class AuthController {

    @Resource
    private AuthorizationService authorizationService;


    @PostMapping(value = "/signIn/{type}")
    public String signIn(@PathVariable("type") String type, User user){
        System.out.println(user);
        Transfer result = authorizationService.signIn(type, user);
        return result.build();
    }

    @Token
    @PostMapping(value = "/logout")
    public String logout(@RequestParam("userName") String userId){
        Transfer result = authorizationService.logout(userId);
        return result.build();
    }

    @PostMapping(value = "/signUp/{type}")
    public String signUp(@PathVariable("type") String type, User user){
        Transfer result = authorizationService.signUp(type, user);
        return result.build();
    }

    @PostMapping(value = "/verify")
    public String verify(@RequestHeader("Authorization") String token){
        boolean res = authorizationService.verifyToken(token.substring(7));
        return res ?
                new Transfer(StatusCode.SUCCESS).build() :
                new Transfer(StatusCode.AUTH_NO_PERMISSION).build();
    }
}
