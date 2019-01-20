package com.lcydream.project.catalina.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import static io.netty.handler.codec.http.HttpHeaderNames.*;

/**
 * MagicResponse
 * 反馈信息封装
 * @author Luo Chun Yun
 * @date 2018/10/20 16:42
 */
public class MagicResponse {

    private ChannelHandlerContext ctx;

    private HttpRequest httpRequest;

    public MagicResponse(ChannelHandlerContext ctx, HttpRequest httpRequest) {
        this.ctx = ctx;
        this.httpRequest = httpRequest;
    }

    /**
     * 输出流控制
     * @param outCxt 输出内容
     */
    public void write(String outCxt) throws Exception{
        try {
            //判断输出内容是否为空
            if(null == outCxt){
                outCxt = "";
            }
            //获取一个http协议的响应参数
            FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(outCxt.getBytes("UTF-8")));
            //设置http的返回参数类型
            httpResponse.headers().set(CONTENT_TYPE, "text/json");
            //设置返回参数的长度
            httpResponse.headers().set(CONTENT_LENGTH, httpResponse.content().readableBytes());
            //设置参数的过期时间
            httpResponse.headers().set(EXPIRES, 0);
            //判断连接是否是长连接
            if (HttpUtil.isKeepAlive(httpRequest)) {
                //设置长连接
                httpResponse.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            }

            ctx.write(httpResponse);
        }finally {
            ctx.flush();
        }
    }
}
