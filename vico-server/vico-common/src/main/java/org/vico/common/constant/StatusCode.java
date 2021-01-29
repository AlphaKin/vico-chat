package org.vico.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCode{
    // 公共
    SUCCESS(0001, null),
    FAILED(0000, null),

    // 登录
    SIGN_IN_UN_NO_EXISTED(2001, "用户名不存在"),
    SIGN_IN_PW_ERROR(2001, "密码错误"),

    // 注册
    SIGN_UP_UN_EXISTED(3001, "用户名已存在"),
    SIGN_UP_PHONE_EXISTED(3002, "手机号已存在"),


    // 其它
    AUTH_NO_PERMISSION(4001, "权限错误"),
    INVALID_PARAMETER(4002, "参数错误"),
    NO_HANDLER(4003, "找不到处理器"),
    BL_NO_SERVER(4004, "找不到服务");


    public int code;
    public String msg;
}