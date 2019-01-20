package com.lcydream.project.replyentity;

import java.io.Serializable;

/**
 * MagicResponse
 *
 * @author Luo Chun Yun
 * @date 2018/9/11 21:04
 */
public class MagicResponse implements Serializable {

    private static final long serialVersionUID = 3767617620493984274L;

    /**
     * 响应码
     */
    private String code;

    /**
     * 返回参数
     */
    private Object data;

    /**
     * 返回信息
     */
    private String message;

    public MagicResponse() {
    }

    public MagicResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public MagicResponse(String code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MagicResponse{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
