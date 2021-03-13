package org.vico.restful.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vico.common.constant.StatusCode;
import org.vico.common.utils.CommonUtil;
import org.vico.restful.mapper.UserMapper;
import org.vico.restful.pojo.User;
import org.vico.restful.utils.Transfer;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class UserController {

    @Resource
    private UserMapper userMapper;

    @PostMapping(value = "/friendList")
    public String getFriendList(@RequestParam("userId") Long userId){
        List<User> friends = userMapper.getFriendList(userId);
        if(CommonUtil.checkEmpty(friends)){
            return new Transfer(StatusCode.SUCCESS)
                    .param("users", friends)
                    .build();
        }
        return new Transfer(StatusCode.FAILED).build();
    }

}