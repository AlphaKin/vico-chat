package org.vico.restful.mapper;

import org.vico.restful.pojo.User;

import java.util.List;


public interface UserMapper {
    List<User> getFriendList(Long userId);
}
