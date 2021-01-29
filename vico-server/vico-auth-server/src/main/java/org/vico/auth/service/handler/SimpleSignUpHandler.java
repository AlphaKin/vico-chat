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
public class SimpleSignUpHandler extends SignUpHandler {

    @Resource
    BCryptPasswordEncoder passwordEncoder;

    @Resource
    UserMapper userMapper;

    SimpleSignUpHandler(){ this.type = SIGN_TYPE.SIMPLE; }

    @Override
    public Transfer apply(User user) {
        System.out.println(user.getUserName());
        if(!CommonUtil.checkEmpty(user, user.getUserName(), user.getUserPassword())){
            throw new RuntimeException("参数无效");
        }

        Transfer transfer = new Transfer();
        if(userMapper.selectOne(new QueryWrapper<User>().eq("user_name", user.getUserName())) != null){
            transfer.status(StatusCode.SIGN_UP_UN_EXISTED);
        }else{
            user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
            try{
                int res = userMapper.insert(user);
                transfer.status(res == 1 ?
                        StatusCode.SUCCESS :
                        StatusCode.FAILED);
            }catch (Exception e){
                transfer.status(StatusCode.FAILED);
            }
        }
        return transfer;
    }
}
