package org.vico.restful.mapper;

import org.apache.ibatis.annotations.Param;
import org.vico.restful.pojo.User;

import java.util.List;


public interface UserMapper {
    List<User> getFriendList(Long userId);

    User selectUserById(@Param("userId") Long userId);
    List<User> selectUsersByUserName(@Param("userName") String userName);
}
