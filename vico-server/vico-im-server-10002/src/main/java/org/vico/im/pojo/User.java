package org.vico.im.pojo;

import java.io.Serializable;

/**
 * null
 * @TableName user
 */
public class User implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private String userName;

    /**
     * 
     */
    private String userPassword;

    /**
     * 
     */
    private String userNickname;

    /**
     * 
     */
    private String userPhone;

    /**
     * 
     */
    private String userEmail;

    /**
     * 
     */
    private Integer userSex;

    /**
     * 
     */
    private Integer userAge;

    /**
     * 
     */
    private String userIntro;

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    private String userHead;


    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    private Long groupId;

    private static final long serialVersionUID = 1L;

    /**
     */
    public Long getId() {
        return id;
    }

    /**
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     */
    public String getUserName() {
        return userName;
    }

    /**
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     */
    public String getUserNickname() {
        return userNickname;
    }

    /**
     */
    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    /**
     */
    public String getUserPhone() {
        return userPhone;
    }

    /**
     */
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    /**
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     */
    public Integer getUserSex() {
        return userSex;
    }

    /**
     */
    public void setUserSex(Integer userSex) {
        this.userSex = userSex;
    }

    /**
     */
    public Integer getUserAge() {
        return userAge;
    }

    /**
     */
    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    /**
     */
    public String getUserIntro() {
        return userIntro;
    }

    /**
     */
    public void setUserIntro(String userIntro) {
        this.userIntro = userIntro;
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
        User other = (User) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
            && (this.getUserPassword() == null ? other.getUserPassword() == null : this.getUserPassword().equals(other.getUserPassword()))
            && (this.getUserNickname() == null ? other.getUserNickname() == null : this.getUserNickname().equals(other.getUserNickname()))
            && (this.getUserPhone() == null ? other.getUserPhone() == null : this.getUserPhone().equals(other.getUserPhone()))
            && (this.getUserEmail() == null ? other.getUserEmail() == null : this.getUserEmail().equals(other.getUserEmail()))
            && (this.getUserSex() == null ? other.getUserSex() == null : this.getUserSex().equals(other.getUserSex()))
            && (this.getUserAge() == null ? other.getUserAge() == null : this.getUserAge().equals(other.getUserAge()))
            && (this.getUserIntro() == null ? other.getUserIntro() == null : this.getUserIntro().equals(other.getUserIntro()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getUserPassword() == null) ? 0 : getUserPassword().hashCode());
        result = prime * result + ((getUserNickname() == null) ? 0 : getUserNickname().hashCode());
        result = prime * result + ((getUserPhone() == null) ? 0 : getUserPhone().hashCode());
        result = prime * result + ((getUserEmail() == null) ? 0 : getUserEmail().hashCode());
        result = prime * result + ((getUserSex() == null) ? 0 : getUserSex().hashCode());
        result = prime * result + ((getUserAge() == null) ? 0 : getUserAge().hashCode());
        result = prime * result + ((getUserIntro() == null) ? 0 : getUserIntro().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userName=").append(userName);
        sb.append(", userPassword=").append(userPassword);
        sb.append(", userNickname=").append(userNickname);
        sb.append(", userPhone=").append(userPhone);
        sb.append(", userEmail=").append(userEmail);
        sb.append(", userSex=").append(userSex);
        sb.append(", userAge=").append(userAge);
        sb.append(", userIntro=").append(userIntro);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}