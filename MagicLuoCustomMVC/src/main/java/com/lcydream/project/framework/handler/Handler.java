package com.lcydream.project.framework.handler;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * Handler HandlerMapping定义
 *
 * @author Luo Chun Yun
 * @date 2018/7/16 22:51
 */
public class Handler {

  /** 定义handler处理的类 */
  private Object controller;

  /** 这里定义执行的方法 */
  private Method method;

  /**
   * 正则表达式，用来匹配不同的方法
   */
  private Pattern pattern;

  public Handler(Object controller, Method method, Pattern pattern) {
    this.controller = controller;
    this.method = method;
    this.pattern = pattern;
  }

  public Object getController() {
    return controller;
  }

  public void setController(Object controller) {
    this.controller = controller;
  }

  public Method getMethod() {
    return method;
  }

  public void setMethod(Method method) {
    this.method = method;
  }

  public Pattern getPattern() {
    return pattern;
  }

  public void setPattern(Pattern pattern) {
    this.pattern = pattern;
  }
}
