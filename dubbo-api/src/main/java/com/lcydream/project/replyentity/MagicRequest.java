package com.lcydream.project.replyentity;

import java.io.Serializable;

/**
 * MagicRequest
 *
 * @author Luo Chun Yun
 * @date 2018/9/11 21:06
 */
public class MagicRequest implements Serializable {

    private static final long serialVersionUID = 4670643675625193421L;

    /**
     * 接收参数
     */
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MagicRequest{" +
                "data='" + data + '\'' +
                '}';
    }
}
