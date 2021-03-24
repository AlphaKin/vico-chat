package org.vico.im.mapper.base;

import org.apache.ibatis.annotations.Param;
import org.vico.im.pojo.MessageRecord;

import java.util.List;

public interface MessageRecordMapper {
    void insertSingleMessageContent(MessageRecord messageRecord);
    void insertSingleMessage(MessageRecord messageRecord);

    void insertSingleOfflineMessage(MessageRecord messageRecord);
    List<MessageRecord> selectOfflineMessage(Long id);
    void deleteOfflineMessage(Long id);

    List<MessageRecord> selectRoamMessages(@Param("userId") Long userId, @Param("timestamp") Long timestamp);
}
