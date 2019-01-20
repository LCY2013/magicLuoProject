package com.lcydream.project.chat.server.handler;

import com.lcydream.project.chat.common.FileSuffixEnum;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URL;

/**
 * HttpHandler
 * http的处理handler
 * @author Luo Chun Yun
 * @date 2018/10/21 16:23
 */
public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    /**
     * 日志记录
     */
    private static Logger logger = Logger.getLogger(HttpHandler.class);

    /**
     * 获取项目的根目录
     */
    private static URL baseURL = HttpHandler.class.getProtectionDomain()
                            .getCodeSource().getLocation();

    /**
     * 定义根目录
     */
    private static final String ROOT_PATH= "webapps";

    /**
     * 根据文件路径获取文件
     * @param fileName 文件名称
     * @return File
     */
    private File getResource(String fileName) throws Exception{
        String path = baseURL.toURI() + ROOT_PATH + "/" + fileName;
        path = path.contains("file:")?path.substring(5):path;
        path = path.replaceAll("//","/");
        return new File(path);
    }

    /**
     *  read0，netty定义方法后面加0的都是实现类的方法，不是接口或者抽象类的方法
     * @param channelHandlerContext 处理器的上下文
     * @param fullHttpRequest 请求参数的封装
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                FullHttpRequest fullHttpRequest) throws Exception{
        //获取客户端请求的url
        String uri = fullHttpRequest.uri();

        //访问目录
        RandomAccessFile file;
        try {
            //如果uri是根目录的情况下，就跳转到chat.html这个首页
            String page = "/".equals(uri)?"chat.html" : uri;
            //获取访问的文件
            file = new RandomAccessFile(getResource(page),"r");
        }catch (Exception e){
            //访问的文件出错就释放这次的连接资源
            channelHandlerContext.fireChannelRead(fullHttpRequest.retain());
            return;
        }

        //获取一个响应的对象
        HttpResponse response = new DefaultHttpResponse(fullHttpRequest.protocolVersion(),
                                    HttpResponseStatus.OK);

        //设置HTTP的响应内容类型
        String contextType = "text/html";
        //根据不同的资源选择协议的类型
        if(uri.endsWith(FileSuffixEnum.CSS)){
            contextType = "text/css";
        }else if(uri.endsWith(FileSuffixEnum.JS)){
            contextType = "text/javascript";
        }else if(uri.toLowerCase().matches("(jpg|png|gif)$")){
            String imageExt = uri.substring(uri.lastIndexOf("."));
            contextType = "image/"+imageExt;
        }

        //设置http的响应头信息的内容信息
        response.headers().set(HttpHeaderNames.CONTENT_TYPE,contextType+";charset=utf-8;");

        //判断是不是长连接
        boolean keepAlive = HttpUtil.isKeepAlive(fullHttpRequest);
        if(keepAlive){
            //设置长连接的文本长度
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,file.length());
            //设置连接为长连接
            response.headers().set(HttpHeaderNames.CONNECTION,HttpHeaderValues.KEEP_ALIVE);
        }

        //通过通道输出响应对象,这里先输出响应的头文件
        channelHandlerContext.write(response);
        //这里输出响应的内容
        channelHandlerContext.write(new DefaultFileRegion(
                file.getChannel(),0,file.length()));
        //channelHandlerContext.write(new ChunkedNioFile(file.getChannel()));

        //获取通道的回调
        ChannelFuture channelFuture = channelHandlerContext
                .writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        //如果不是长连接就关闭这个回调的Future
        if(!keepAlive){
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        }

        file.close();
    }

    /**
     * 出现异常的情况会调用这里
     * @param ctx 通道上下文
     * @param cause 异常原因
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Channel channel = ctx.channel();
        logger.info("IP address : " + channel.remoteAddress() + " 异常");
        //当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
