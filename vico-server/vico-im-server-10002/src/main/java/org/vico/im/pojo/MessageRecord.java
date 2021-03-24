package org.vico.im.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * null
 */
public class MessageRecord implements Serializable {
    /**
     * 
     */
    private Long msgcId;

    /**
     * 
     */
    private Integer msgcStatus;

    /**
     * 
     */
    private String msgcContent;

    /**
     * 
     */
    private Integer mtId;

    /**
     * 
     */
    private Timestamp msgcSendtime;


    private Long msgId;
    private Long fromId;
    private Long toId;
    private Long contentId;

    private Boolean isGroup;

    private Long groupId;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }


    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public Long getToId() {
        return toId;
    }

    public void setToId(Long toId) {
        this.toId = toId;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Boolean getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(Boolean group) {
        isGroup = group;
    }

    private static final long serialVersionUID = 1L;

    /**
     */
    public Long getMsgcId() {
        return msgcId;
    }

    /**
     */
    public void setMsgcId(Long msgcId) {
        this.msgcId = msgcId;
    }

    /**
     */
    public Integer getMsgcStatus() {
        return msgcStatus;
    }

    /**
     */
    public void setMsgcStatus(Integer msgcStatus) {
        this.msgcStatus = msgcStatus;
    }

    /**
     */
    public String getMsgcContent() {
        return msgcContent;
    }

    /**
     */
    public void setMsgcContent(String msgcContent) {
        this.msgcContent = msgcContent;
    }

    /**
     */
    public Integer getMtId() {
        return mtId;
    }

    /**
     */
    public void setMtId(Integer mtId) {
        this.mtId = mtId;
    }

    /**
     */
    public Timestamp getMsgcSendtime() {
        return msgcSendtime;
    }

    /**
     */
    public void setMsgcSendtime(Timestamp msgcSendtime) {
        this.msgcSendtime = msgcSendtime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        MessageRecord other = (MessageRecord) that;
        return (this.getMsgcId() == null ? other.getMsgcId() == null : this.getMsgcId().equals(other.getMsgcId()))
            && (this.getMsgcStatus() == null ? other.getMsgcStatus() == null : this.getMsgcStatus().equals(other.getMsgcStatus()))
            && (this.getMsgcContent() == null ? other.getMsgcContent() == null : this.getMsgcContent().equals(other.getMsgcContent()))
            && (this.getMtId() == null ? other.getMtId() == null : this.getMtId().equals(other.getMtId()))
            && (this.getMsgcSendtime() == null ? other.getMsgcSendtime() == null : this.getMsgcSendtime().equals(other.getMsgcSendtime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMsgcId() == null) ? 0 : getMsgcId().hashCode());
        result = prime * result + ((getMsgcStatus() == null) ? 0 : getMsgcStatus().hashCode());
        result = prime * result + ((getMsgcContent() == null) ? 0 : getMsgcContent().hashCode());
        result = prime * result + ((getMtId() == null) ? 0 : getMtId().hashCode());
        result = prime * result + ((getMsgcSendtime() == null) ? 0 : getMsgcSendtime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", msgcId=").append(msgcId);
        sb.append(", msgcStatus=").append(msgcStatus);
        sb.append(", msgcContent=").append(msgcContent);
        sb.append(", mtId=").append(mtId);
        sb.append(", msgcSendtime=").append(msgcSendtime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}