package org.vico.restful.mapper;


import org.vico.restful.pojo.Group;
import org.vico.restful.pojo.User;

import java.util.List;

/**
 * @Entity generate.Group
 */
public interface GroupMapper {
    /**
     *
     * @mbg.generated 2021-02-11 01:26:17
     */
    int deleteByPrimaryKey(Long grpId);

    /**
     *
     * @mbg.generated 2021-02-11 01:26:17
     */
    int insert(Group record);

    /**
     *
     * @mbg.generated 2021-02-11 01:26:17
     */
    int insertSelective(Group record);

    /**
     *
     * @mbg.generated 2021-02-11 01:26:17
     */
    Group selectByPrimaryKey(Long grpId);

    /**
     *
     * @mbg.generated 2021-02-11 01:26:17
     */
    int updateByPrimaryKeySelective(Group record);

    /**
     *
     * @mbg.generated 2021-02-11 01:26:17
     */
    int updateByPrimaryKey(Group record);



    List<Group> selectByUserId(Long userId);
}