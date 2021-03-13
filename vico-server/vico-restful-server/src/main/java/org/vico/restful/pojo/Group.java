package org.vico.restful.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
@TableName(value = "group" )
public class Group {

    @TableId(value = "id")
    private Long id;

    @TableField("grp_name")
    private String groupName;

    @TableField("grp_intro")
    private String groupIntro;

    @TableField("grp_create_time")
    private Timestamp createTime;

    @TableField("grp_own_id")
    private String ownUserId;

}
