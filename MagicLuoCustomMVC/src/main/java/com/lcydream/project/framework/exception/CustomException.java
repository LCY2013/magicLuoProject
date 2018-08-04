package com.lcydream.project.framework.exception;

/**
 * CustomException
 *
 * @author Luo Chun Yun
 * @date 2018/7/21 22:49
 */
public class CustomException extends Exception {
    /**
     * 自定义异常
      * @param message
     * @param cause
     */
  public CustomException(String message, Throwable cause) {
    super(message, cause);
  }
}
