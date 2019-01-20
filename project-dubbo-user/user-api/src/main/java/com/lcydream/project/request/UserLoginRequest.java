package com.lcydream.project.request;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * UserLoginRequest
 *
 * @author Luo Chun Yun
 * @date 2018/9/16 10:39
 */
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = -4841152980009836996L;

    @NotNull(message = "用户名不能为空")
    private String name;

    @NotNull(message = "密码不能为空")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserLoginRequest{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
