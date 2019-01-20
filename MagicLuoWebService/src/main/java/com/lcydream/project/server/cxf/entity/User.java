package com.lcydream.project.server.cxf.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * User
 * 实体类
 * @author Luo Chun Yun
 * @date 2018/8/25 20:21
 */
@XmlRootElement
public class User {

    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
