package com.lcydream.project.request;

import com.lcydream.project.entity.Request;

import java.io.Serializable;
import java.util.Date;

/**
 * UserRegisterRequest
 *
 * @author Luo Chun Yun
 * @date 2018/11/19 10:43
 */
public class UserRegisterRequest extends Request implements Serializable {

    private static final long serialVersionUID = 8603952642893057252L;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 真实姓名
     */
    private String realname;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 性别
     */
    private String sex;
    /**
     * 状态（1正常，2冻结）
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;



}
