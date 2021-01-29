package org.vico.auth.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import org.vico.common.annotation.Json;


@Data
@ToString
@TableName(value = "user" )
@Json(excludeField = { "id", "userPassword" })
public class User {

    @TableId(value = "id")
    private Long id;

    @TableField("user_name")
    private String userName;

    @TableField("user_password")
    private String userPassword;

    @TableField("user_nickName")
    private String userNickName;

    @TableField("user_phone")
    private String userPhone;

    @TableField("user_email")
    private String userEmail;

    @TableField("user_sex")
    private int userSex;

    @TableField("user_age")
    private int userAge;

    @TableField("user_intro")
    private String userIntro;

}