package org.vico.auth.service.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.vico.auth.mapper.UserMapper;
import org.vico.auth.pojo.User;
import org.vico.auth.utils.Transfer;
import org.vico.common.constant.StatusCode;
import org.vico.common.utils.CommonUtil;

import javax.annotation.Resource;

@Component
public class SimpleSignInHandler extends SignInHandler {
    SimpleSignInHandler(){
        this.type = SIGN_TYPE.SIMPLE;
    }

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Resource
    private UserMapper userMapper;

    @Override
    public Transfer apply(User user) {
        if(!CommonUtil.checkEmpty(user, user.getUserName(), user.getUserPassword())){
            return new Transfer(StatusCode.INVALID_PARAMETER);
        }
        User res = userMapper.selectOne(new QueryWrapper<User>().eq("user_name", user.getUserName()));

        if(res == null){
            return new Transfer(StatusCode.SIGN_IN_UN_NO_EXISTED);
        }
        user.setId(res.getId());
        return passwordEncoder.matches(user.getUserPassword(), res.getUserPassword()) ?
                new Transfer(StatusCode.SUCCESS).param("user", res) :
                new Transfer(StatusCode.SIGN_IN_PW_ERROR);
    }
}
