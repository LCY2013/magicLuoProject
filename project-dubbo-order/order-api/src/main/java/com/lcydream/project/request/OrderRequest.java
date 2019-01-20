package com.lcydream.project.request;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * OrderRequest
 *
 * @author Luo Chun Yun
 * @date 2018/9/16 14:32
 */
public class OrderRequest implements Serializable {

    private static final long serialVersionUID = 5110258375570023345L;

    /**
     * 请求参数
     */
    @NotNull(message = "生成订单参数不能为空")
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "data='" + data + '\'' +
                '}';
    }
}
