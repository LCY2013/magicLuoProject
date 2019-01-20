package com.lcydream.project.catalina.http;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;

/**
 * MagicRequest
 * 请求信息封装
 * @author Luo Chun Yun
 * @date 2018/10/20 16:42
 */
public class MagicRequest {

    /**
     * 处理器的上下文
     */
    private ChannelHandlerContext ctx;

    /**
     * 请求信息封装
     */
    private HttpRequest httpRequest;

    /**
     * 重写构造函数，用于初始化请求内容
     * @param ctx 连接的文本信息
     * @param httpRequest 请求信息的封装
     */
    public MagicRequest(ChannelHandlerContext ctx, HttpRequest httpRequest) {
        this.ctx = ctx;
        this.httpRequest = httpRequest;
    }

    /**
     * 获取URI的信息
     * @return uri的路径
     */
    public String Uri(){
        return httpRequest.uri();
    }

    /**
     * 获取请求的处理方法
     * @return 返回方法的字符串
     */
    public String getMethod(){
        return httpRequest.method().name();
    }

    /**
     * 获取所有的请求参数
     * @return
     */
    public Map<String, List<String>> getParameters(){
        QueryStringDecoder queryStringDecoder =
                new QueryStringDecoder(httpRequest.uri());
        return queryStringDecoder.parameters();
    }

    /**
     * 根据请求参数的名称获取参数值
     * @param name 参数名
     * @return 返回参数的值
     */
    public String getParameter(String name){
        Map<String, List<String>> parameters = getParameters();
        List<String> value = parameters.get(name);
        if(null == value){
            return null;
        }else {
            return value.get(0);
        }

    }

    /**
     * 获取请求的Http版本号
     * @return 版本号信息
     */
    public String getHttpVersion(){
        HttpVersion httpVersion = httpRequest.protocolVersion();
        return httpVersion.protocolName()+":"+httpVersion.majorVersion()+":"+httpVersion.minorVersion();
    }

    /**
     * 获取头信息
     * @return 头信息
     */
    public String getHeader(){
        HttpHeaders headers = httpRequest.headers();
        return JSON.toJSONString(headers);
    }

}
