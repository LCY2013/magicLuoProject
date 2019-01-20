package com.lcydream.project.server.cxf.response;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * ResponseResult
 *
 * @author Luo Chun Yun
 * @date 2018/8/25 20:28
 */
@XmlRootElement
public class ResponseResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;

    private String msg;

    private Object data;

    public ResponseResult() {
    }

    public ResponseResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
