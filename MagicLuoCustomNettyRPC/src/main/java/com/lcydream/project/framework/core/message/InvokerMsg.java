package com.lcydream.project.framework.core.message;

import java.io.Serializable;

/**
 * InvokerMsg
 * RPC 序列化类
 * @author Luo Chun Yun
 * @date 2018/11/11 16:41
 */
public class InvokerMsg implements Serializable {

    private static final long serialVersionUID = 3555037943562825548L;

    /**
     * 调用的服务名称
     */
    private String className;

    /**
     * 调用的方法名称
     */
    private String methodName;

    /**
     * 参数列表
     */
    private Class<?>[] params;

    /**
     * 参数值
     */
    private Object[] values;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParams() {
        return params;
    }

    public void setParams(Class<?>[] params) {
        this.params = params;
    }

    public Object[] getValues() {
        return values;
    }

    public void setValues(Object[] values) {
        this.values = values;
    }
}
