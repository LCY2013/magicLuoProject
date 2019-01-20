package com.lcydream.project.entity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Request
 *s
 * @author Luo Chun Yun
 * @date 2018/9/16 10:32
 */
public class Request implements Serializable {
    private static final long serialVersionUID = 5265582088479655992L;

    /**
     * 请求参数
     */
    @NotNull(message = "请求Token不能为空")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Request{" +
                "token='" + token + '\'' +
                '}';
    }
}
