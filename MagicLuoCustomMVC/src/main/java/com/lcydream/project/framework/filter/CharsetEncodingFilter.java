package com.lcydream.project.framework.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * CharsetEncodingFilter
 * 字符编码过滤器
 *
 * @author Luo Chun Yun
 * @date 2018/7/22 17:25
 */
public class CharsetEncodingFilter implements Filter {

    /**
     * 定义项目的字符编码，默认UTF-8
     */
    public static String CHARSETENCODING = "UTF-8";

    /**
     * 做初始化操作，读取filter的字符编码
     *
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //获取字符编码格式
        String charsetEncoding = filterConfig.getInitParameter("CharsetEncoding");
        //初始化编码格式
        CharsetEncodingFilter.CHARSETENCODING = charsetEncoding;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 设置访问的字符编码
        request.setCharacterEncoding(CHARSETENCODING);
        response.setCharacterEncoding(CHARSETENCODING);
        chain.doFilter(request, response);
    }

}
