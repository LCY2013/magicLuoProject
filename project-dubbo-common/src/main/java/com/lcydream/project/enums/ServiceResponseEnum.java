package com.lcydream.project.enums;

/**
 * ServiceResponseEnum
 * 微服务响应设置
 * @author Luo Chun Yun
 * @date 2018/11/19 14:09
 */
public enum ServiceResponseEnum {

    SERVICE_SUCCESS(0,"成功"),
    SERVICE_FAILED(1,"系统繁忙,请稍后重试"),
    SERVICE_VLIDPARAMFAILED(3,"参数校验失败"),
    SERVICE_REQUESTFAILED(4,"请求不合法"),
    ;

    private Integer code;

    private String message;

    ServiceResponseEnum(Integer code, String message) {
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
