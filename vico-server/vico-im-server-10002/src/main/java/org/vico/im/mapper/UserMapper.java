package org.vico.im.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.vico.im.pojo.User;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    List<Long> selectAllUserIdInGroup(Long groupId);
}
