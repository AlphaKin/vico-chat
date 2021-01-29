package org.vico.auth.service.handler;

import org.springframework.stereotype.Component;
import org.vico.auth.pojo.User;
import org.vico.auth.utils.Transfer;
import org.vico.common.constant.StatusCode;

@Component
public class PhoneSignUpHandler extends SignUpHandler {
    PhoneSignUpHandler(){
        this.type = SIGN_TYPE.PHONE;
    }
    @Override
    public Transfer apply(User user) {

        return new Transfer(StatusCode.FAILED);
    }
}
