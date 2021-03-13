package org.vico.restful.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vico.common.constant.StatusCode;
import org.vico.restful.pojo.Group;
import org.vico.restful.service.GroupService;
import org.vico.restful.utils.Transfer;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class GroupController {

    @Resource
    private GroupService groupService;

    @PostMapping(value = "groupList")
    public String getGroupList(@RequestParam("userId") Long userId){
        List<Group> list = groupService.getGroupsByUserId(userId);
        return new Transfer(StatusCode.SUCCESS)
                .param("groups", list)
                .build();
    }
}
