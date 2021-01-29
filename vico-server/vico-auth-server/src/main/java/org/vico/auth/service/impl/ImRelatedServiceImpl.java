package org.vico.auth.service.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.vico.auth.service.ImRelatedService;

import lombok.val;
import org.vico.common.pojo.ImServerMetaInfo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ImRelatedServiceImpl implements ImRelatedService {

    @Resource
    RedisTemplate redisTemplate;

    @Override
    public ImServerMetaInfo choose() {
        val list = getServerList();
        if(list == null || list.size() == 0){
            return null;
        }
        ImServerMetaInfo metaInfo = list.get(0);
        for(int i=1; i<list.size(); ++i){
            if(metaInfo.getConnectingCount() > list.get(i).getConnectingCount()){
                metaInfo = list.get(i);
            }
        }
        return metaInfo;
    }

    @Override
    public List<ImServerMetaInfo> getServerList() {
        Set<String> keys = redisTemplate.opsForHash().keys("IM_SERVER_META#");
        List<ImServerMetaInfo> list = new ArrayList<>();
        keys.forEach((str) -> {
            list.add((ImServerMetaInfo) redisTemplate.opsForHash().get("IM_SERVER_META#", str));
        });
        return list;
    }

    @Override
    public int getServerCount() {
        return redisTemplate.opsForHash().keys("IM_SERVER_META").size();
    }
}
