package org.vico.restful.service;

import org.vico.restful.pojo.Group;

import java.util.List;

public interface GroupService {
    List<Group> getGroupsByUserId(Long userId);
}
