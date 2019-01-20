package com.lcydream.project.framework.server.ioc.utils;

/**
 * StringUtils
 * 字符操作工具类
 * @author Luo Chun Yun
 * @date 2018/7/17 22:13
 */
public class StringUtils {

	/**
	 * 字符串首字符小写
	 * @param charString
	 * @return
	 */
	public static String lowerFirstCharForString(String charString){
	    /*char[] chars = charString.toCharArray();
	    chars[0]+=32;
	    return new String(chars);*/
	    if(charString == null || "".equals(charString)){
			return charString;
	    }
	    if (Character.isLowerCase(charString.charAt(0))) {
	      return charString;
        } else {
	      return (new StringBuilder())
	          .append(Character.toLowerCase(charString.charAt(0)))
	          .append(charString.substring(1))
	          .toString();
	    }
	}

	/**
	 * 字符串首字符大写
	 * @param charString
	 * @return
	 */
	public static String upperFirstCharForString(String charString){
		if(charString == null || "".equals(charString)){
			return charString;
		}
		if(Character.isUpperCase(charString.charAt(0))){
			return charString;
		}else{
			return (new StringBuilder()).append(Character.toUpperCase(charString.charAt(0))).append(charString.substring(1)).toString();
		}
	}
}
