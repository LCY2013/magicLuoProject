package com.lcydream.project.business.controller;

import com.lcydream.project.business.service.IMagicService;
import com.lcydream.project.framework.annotation.*;
import com.lcydream.project.framework.model.ModelAndView;
import com.lcydream.project.framework.utils.MethodParameterNameUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * CustomController
 *
 * @author Luo Chun Yun
 * @date 2018/7/18 21:41
 */
@CustomComtroller
@CustomRequestMapping("/luochunyun")
public class MagicLuoCustomController {

  @CustomAwtowired private IMagicService iMagicService;

  @CustomRequestMapping("/magic/.*.magic")
  public void test(
      HttpServletRequest req,
      HttpServletResponse resp,
      @CustomRequestParam(value = "names") String name,
      @CustomRequestParam(value = "ages") Integer age) {
    iMagicService.sayHello();
    out(resp, "hello,[name=" + name + "],[age=" + age + "],welcome to magic luo MVC world");
  }

  @CustomRequestMapping("/luo/.*.magic")
  public void luo(
            HttpServletRequest req,
            HttpServletResponse resp,
            @CustomRequestParam(value = "ages") Integer age) {
        iMagicService.sayHello();
        out(resp, "hello,[age=" + age + "],welcome to magic luo MVC world");
    }

  @CustomRequestMapping("//model/.*.magic")
  private ModelAndView model(@CustomRequestParam(value = "name") String name,
                             String addr) {
    Map map = new HashMap();
    map.put("name", name);
    map.put("addr", addr);
    System.out.println("name:" + name + "\naddr:" + addr);
    ModelAndView modelAndView = new ModelAndView("/hello.mluo", map);
    return modelAndView;
  }

  @CustomRequestMapping("/json.magic")
  @CustomResponseBody
  private Object json(@CustomRequestParam(value = "name") String name) {
    Map map = new HashMap();
    map.put("name", name);
    map.put("age", "18");
    map.put("addr", "成都");
    return map;
  }

  private void out(HttpServletResponse resp, String outString) {
    try {
      resp.getWriter().write(outString);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    Method[] declaredMethods = MagicLuoCustomController.class.getDeclaredMethods();
    for (Method method : declaredMethods) {
      if (method.getName().equals("model")) {
        MethodParameterNameUtils.getParameterNamesByAsm5(MagicLuoCustomController.class, method);
      }
    }
  }
}
