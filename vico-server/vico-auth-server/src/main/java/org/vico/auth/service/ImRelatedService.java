package org.vico.auth.service;

import org.vico.common.pojo.ImServerMetaInfo;

import java.util.List;

public interface ImRelatedService {
    ImServerMetaInfo choose();
    List<ImServerMetaInfo> getServerList();
    int getServerCount();
}
