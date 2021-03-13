package org.vico.restful.service.impl;

import org.springframework.stereotype.Service;
import org.vico.restful.mapper.GroupMapper;
import org.vico.restful.pojo.Group;
import org.vico.restful.service.GroupService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Resource
    private GroupMapper groupMapper;

    @Override
    public List<Group> getGroupsByUserId(Long userId) {
        return groupMapper.selectByUserId(userId);
    }
}
