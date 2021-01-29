package org.vico.restful.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value = "friend_relation")
public class FriendRelation {

    @TableId("id")
    private Long id;

    @TableField("user_a_id")
    private Long aid;

    @TableField("user_b_id")
    private Long bid;

    @TableField("friend_policy_id")
    private Long fpId;

}
