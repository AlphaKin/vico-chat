package org.vico.auth.service.handler;

import lombok.AllArgsConstructor;
import org.vico.auth.pojo.User;
import org.vico.auth.utils.Transfer;


public abstract class SignBaseHandler {

    @AllArgsConstructor
    public enum SIGN_TYPE{
        SIMPLE(),
        PHONE(),
        MAIL()
    }

    protected SIGN_TYPE type;
    public abstract Transfer apply(User user);
    public boolean hit(SIGN_TYPE type){
        return type.equals(this.type);
    }
}
