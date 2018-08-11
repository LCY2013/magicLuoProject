package com.lcydream.project.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RegularExpressionUtils
 * 正则表达式工具
 * <p>
 * eg:匹配一段字符中#{}这里面有至少一个任意的字符
 * Pattern.compile("#\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
 *
 * @author Luo Chun Yun
 * @date 2018/7/22 17:15
 */
public class RegularExpressionUtils {

    /**
     * 正则表达式匹配两个指定字符串中间的内容，存在多个就返回一个list集合
     *
     * @param matcher 需要匹配的字符串
     * @param rgex    正则表达式
     * @return
     */
    public static List<String> getRegular(String matcher, String rgex) {
        List<String> list = new ArrayList<String>();
        // 匹配的模式
        Pattern pattern = Pattern.compile(rgex);
        Matcher m = pattern.matcher(matcher);
        while (m.find()) {
            int i = 1;
            list.add(m.group(i));
            i++;
        }
        return list;
    }

    /**
     * 正则表达式匹配两个指定字符串中间的内容，存在多个也只返回一个
     *
     * @param matcher 需要匹配的字符串
     * @param rgex    正则表达式
     * @return
     */
    public static String getSimpleRegular(String matcher, String rgex) {
        // 匹配的模式
        Pattern pattern = Pattern.compile(rgex);
        Matcher m = pattern.matcher(matcher);
        while (m.find()) {
            return m.group(1);
        }
        return "";
    }
}
