package com.lcydream.project.framework.adapter;

import com.alibaba.fastjson.JSON;
import com.lcydream.project.framework.annotation.CustomRequestParam;
import com.lcydream.project.framework.annotation.CustomResponseBody;
import com.lcydream.project.framework.handler.Handler;
import com.lcydream.project.framework.model.ModelAndView;
import com.lcydream.project.framework.utils.MethodParameterNameUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * HandlerAdapter 方法适配器
 *
 * @author Luo Chun Yun
 * @date 2018/7/16 22:54
 */
public class HandlerAdapter {

  /** 存放方法的实际参数 */
  private Map<String, Integer> paramMapping;
  /** 存放注解参数与实际形参的对应关系 */
  private Map<String, String> annotationParamMapping;
	/**
	 * 适配器的特定处理器
	 */
	private Handler handler;

  public HandlerAdapter(
		  Map<String, Integer> paramMapping, Map<String, String> annotationParamMapping, Handler handler) {
    this.paramMapping = paramMapping;
    this.annotationParamMapping = annotationParamMapping;
	  this.handler = handler;
  }

	public Handler getHandler() {
		return handler;
	}

	/**
   * 调用url对应的method，这里用HttpServletRequest和HttpServletResponse是为了赋值给某些方法
   *
   * @param req
   * @param resp
	 */
    public Object handle(HttpServletRequest req, HttpServletResponse resp)
      throws Exception {
    // 获取这个适配器的参数列表
    Class<?>[] parameterTypes = handler.getMethod().getParameterTypes();
    // 方法的参数只能通过索引赋值，不能通过名字赋值,获取传入的参数
    Map<String, String[]> parameterMap = req.getParameterMap();
    // 定义一个中间Map用于做方法参数赋值
    Map<String, String> castMap = new HashMap<>();
    castMap.putAll(annotationParamMapping);
    // 用户存放方法的参数
    Object[] paramValues = new Object[parameterTypes.length];
    // 遍历map
    for (Map.Entry<String, String[]> param : parameterMap.entrySet()) {
      // 解决掉java方法toString的多余字符
      String value =
          Arrays.toString(param.getValue()).replaceAll("\\[|\\]", "").replaceAll(",\\s", ",");
      // 如果说方法的形式参数，或者注解的参数名称没有找打满足需求的赋值名称，则这个赋值参数不存在赋值的需求，不需要处理
      if (!this.paramMapping.containsKey(param.getKey())
          && !castMap.containsValue(param.getKey())) {
        continue;
      }
      // 获取方法实际形式参数的序列号
      Integer index = null;
      // 如果注解参数中查找中没有这个名称，就去实际的形式参数中查找
      if (castMap.containsValue(param.getKey())) {
        // 根据从注解中获取到实际形式参数的名称
        for (Map.Entry<String, String> entry : castMap.entrySet()) {
          // 如果取到了这个参数就获取这个形式参数的位置
          if (entry.getValue().equals(param.getKey())) {
            // 获取这个参数的真实参数位置
            index = this.paramMapping.get(entry.getKey());
            // 赋值过后的参数移除
            castMap.remove(entry.getKey());
            break;
          }
        }
      } else {
        /** 这里做的处理是当注解中的参数名称不匹配时就利用原有的形式参数名称去赋值 */
        index = this.paramMapping.get(param.getKey());
        if (paramValues[index] != null) {
          index = null;
        }
      }
      if (index != null && index < parameterTypes.length) {
        // 按类型进行注入值
        paramValues[index] = MethodParameterNameUtils.castValue(value, parameterTypes[index]);
      }
    }
    int i = 0;
    // 最后给request的参数赋值
    for (Class clazz : parameterTypes) {
      if (clazz == HttpServletRequest.class) {
        paramValues[i] = req;
        i++;
      } else if (clazz == HttpServletResponse.class) {
        paramValues[i] = resp;
        i++;
      }
    }
    // 检查参数是否有非必填选项
    Annotation[][] annotations = handler.getMethod().getParameterAnnotations();
    if (annotations != null || annotations.length > 0) {
      // 如果方法参数存在注解，就按注解赋值
      for (int j = 0; j < annotations.length; j++) {
        // 遍历每一个形参的所有注解
        for (Annotation annotation : annotations[j]) {
          // 如果是requestParam，我们就进行处理
          if (annotation instanceof CustomRequestParam) {
            // 取出注解参数是否是必填
            boolean required = ((CustomRequestParam) annotation).required();
            if (required
                && paramValues[j] == null
                && !"".equals(((CustomRequestParam) annotation).value())) {
              throw new RuntimeException(
                  ((CustomRequestParam) annotation).value()
                      + " is Required,you can set this required=false");
            }
          }
        }
      }
    }
      //判断方法的返回是否是modelAndView对象
      boolean isModelAndView = handler.getMethod().getReturnType() == ModelAndView.class ? true : false;
      //开启私有方法的访问权限
      handler.getMethod().setAccessible(true);
      // 最后执行
      Object invoke = handler.getMethod().invoke(handler.getController(), paramValues);
      //如果是ModelAndView对象就返回该对象
      if (isModelAndView && invoke != null) {
        ModelAndView modelAndView = (ModelAndView) invoke;
        return modelAndView;
      } else if (!isModelAndView && invoke != null) {
        //如果不是该对象就查看是否加了@CustomeResPonseBody注解
        if (handler.getMethod().isAnnotationPresent(CustomResponseBody.class)) {
          return JSON.toJSONString(invoke);
        } else {
          //如果没有加@CustomeResPonseBody注解，就看是不是返回的视图路径
          return new ModelAndView(invoke);
        }
      }
      return null;
  }
}
