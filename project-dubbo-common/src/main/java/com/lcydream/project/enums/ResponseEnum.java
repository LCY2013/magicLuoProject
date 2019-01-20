package com.lcydream.project.enums;

/**
 * ResponseEnum
 *
 * @author Luo Chun Yun
 * @date 2018/9/16 11:31
 */
public enum ResponseEnum {

    SUCCESS(0,"成功"),
    FAILED(1,"系统繁忙,请稍后重试"),
    VLIDPARAMFAILED(3,"参数校验失败"),
    REQUESTFAILED(4,"请求不合法"),
    TOKEN_EXPIRE(5,"token失效"),
    SIGNATURE_ERROR(6,"签名失败"),
    ;

    private Integer code;

    private String message;

    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
