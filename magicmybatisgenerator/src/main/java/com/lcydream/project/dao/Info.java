package com.lcydream.project.dao;

import java.io.Serializable;
import java.util.Date;

public class Info implements Serializable {
    private Long id;

    private String name;

    private Date birthday;

    private Date createtime;

    private Byte fans;

    private Date time;

    private Float money;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Byte getFans() {
        return fans;
    }

    public void setFans(Byte fans) {
        this.fans = fans;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", birthday=").append(birthday);
        sb.append(", createtime=").append(createtime);
        sb.append(", fans=").append(fans);
        sb.append(", time=").append(time);
        sb.append(", money=").append(money);
        sb.append("]");
        return sb.toString();
    }
}