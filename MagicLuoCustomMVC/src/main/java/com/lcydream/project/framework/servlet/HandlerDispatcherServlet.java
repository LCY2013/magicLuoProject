package com.lcydream.project.framework.servlet;

import com.lcydream.project.framework.adapter.HandlerAdapter;
import com.lcydream.project.framework.annotation.CustomComtroller;
import com.lcydream.project.framework.annotation.CustomRequestMapping;
import com.lcydream.project.framework.annotation.CustomRequestParam;
import com.lcydream.project.framework.context.HandlerApplicationContext;
import com.lcydream.project.framework.exception.CustomException;
import com.lcydream.project.framework.filter.CharsetEncodingFilter;
import com.lcydream.project.framework.handler.Handler;
import com.lcydream.project.framework.model.ModelAndView;
import com.lcydream.project.framework.resolver.ViewResolver;
import com.lcydream.project.framework.utils.MethodParameterNameUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HandlerDispatcherServlet
 *
 * @author Luo Chun Yun
 * @date 2018/7/16 22:02
 */
public class HandlerDispatcherServlet extends HttpServlet {

  private static final String LOCATION = "contextConfigLocation";
  private static final String URL_PPATTERN = "/";
  /** map结构的handler映射 */
  //private Map<Pattern, Handler> handlerMapping = new HashMap<Pattern, Handler>();

  /** 这里的list结构用来保存mapping映射，借鉴了SpringMVC的思想 */
  private List<Handler> handlerMapping = new ArrayList<>();

  /**
   * 处理的适配器
   */
  private List<HandlerAdapter> adapterMap = new ArrayList<>();
  /**
   * 模板解析器
   */
  private List<ViewResolver> viewResolvers = new ArrayList<>();


  /**
   * 初始化自定义的IOC容器
   *
   * @param config
   * @throws ServletException
   */
  @Override
  public void init(ServletConfig config) throws ServletException {
    // IOC容器初始化
    HandlerApplicationContext context =
        new HandlerApplicationContext(config.getInitParameter(LOCATION));
    /*Map ioc = context.getAll();

    System.out.println(ioc);

    System.out.println("magicService：" + context.getBean("magicService"));
    System.out.println("IMagicService.class:" + context.getBean(IMagicService.class));
    System.out.println("MagicServiceImpl.class:" + context.getBean(MagicServiceImpl.class));
    System.out.println(
        "magicGetiService:"
            + ((MagicServiceImpl) context.getBean(MagicServiceImpl.class)).getiService());
    System.out.println(
        "magicGetService:"
            + ((MagicServiceImpl) context.getBean(MagicServiceImpl.class)).getService());
    System.out.println("service:" + context.getBean("service"));
    System.out.println("IService.class:" + context.getBean(IService.class));
    System.out.println("ServiceImpl.class:" + context.getBean(ServiceImpl.class));*/

    // 请求解析
    initMultipartResolver(context);
    // 多语言、国际化
    initLocaleResolver(context);
    // 主题View层的
    initThemeResolver(context);

    // ============== 重要 ================
    // 解析url和Method的关联关系
    initHandlerMappings(context);
    // 适配器（匹配的过程）
    initHandlerAdapters(context);
    // ============== 重要 ================

    // 异常解析
    initHandlerExceptionResolvers(context);
    // 视图转发（根据视图名字匹配到一个具体模板）
    initRequestToViewNameTranslator(context);

    // 解析模板中的内容（拿到服务器传过来的数据，生成HTML代码）
    initViewResolvers(context);

    initFlashMapManager(context);
    System.out.println("servlet started");
  }

  /**
   * 请求解析
   *
   * @param context
   */
  private void initMultipartResolver(HandlerApplicationContext context) {}

  /**
   * 多语言、国际化
   *
   * @param context
   */
  private void initLocaleResolver(HandlerApplicationContext context) {}

  /**
   * 主题View层的
   *
   * @param context
   */
  private void initThemeResolver(HandlerApplicationContext context) {}

  /**
   * 解析url和Method的关联关系
   *
   * @param context
   */
  private void initHandlerMappings(HandlerApplicationContext context) {
    // 只要是由Controller注解修饰的类，里面的方法如果存在requestMapping的注解就映射出来
    // 不加requestMapping注解的方法就不用映射路径

    Map<String, Object> all = context.getAll();
    if (all == null || all.size() == 0) {
      return;
    }
    for (Map.Entry<String, Object> entry : all.entrySet()) {
      // 判断注册的实体类中是否存在Controller注解
      Class clazz = entry.getValue().getClass();
      if (!clazz.isAnnotationPresent(CustomComtroller.class)) {
        continue;
      }
      // 定义该controller这个bean中的根路径
      StringBuffer controllerUrl = new StringBuffer();
      // 如果存在该注解表示这个类是一个controller，接着判断这个类上是否存在requestMapping注解
      if (clazz.isAnnotationPresent(CustomRequestMapping.class)) {
        // 获取类上面的所有requestMapping注解的值
        Annotation[] annotations = clazz.getAnnotationsByType(CustomRequestMapping.class);
        // 构建这个映射路径
        for (Annotation annotation : annotations) {
          CustomRequestMapping customRequestMapping = (CustomRequestMapping) annotation;
          if (customRequestMapping.value() != null && !"".equals(customRequestMapping.value())) {
            // 判断是否是以/开头的映射路径
            if (!"".equals(customRequestMapping.value())
                && !customRequestMapping.value().startsWith("/")) {
              controllerUrl.append("/");
            }
            // 拼接映射
            controllerUrl.append(customRequestMapping.value());
            // 处理以/结尾的情况
            if (customRequestMapping.value().endsWith("/")) {
              controllerUrl.replace(
                  controllerUrl.lastIndexOf("/"), controllerUrl.lastIndexOf("/"), "");
            }
          }
        }
      }
      // 扫描controller类下面的方法
      Method[] methods = clazz.getDeclaredMethods();
      // 将方法上带有requestMapping注解的方法取值
      for (Method method : methods) {
        //将类上的路径匹配给新的变量
        StringBuffer methodControllerUrl = new StringBuffer();
        methodControllerUrl.append(controllerUrl.toString());
        // 不存在就跳过对这个方法做映射
        if (!method.isAnnotationPresent(CustomRequestMapping.class)) {
          continue;
        }
        // 映射方法的url
        StringBuffer methodUrl = new StringBuffer();
        // 如果存在就取到这个映射的值
        CustomRequestMapping[] annotationsByType =
            method.getAnnotationsByType(CustomRequestMapping.class);
        // 取出方法的url映射
        for (CustomRequestMapping customRequestMapping : annotationsByType) {
          if (customRequestMapping.value() != null && !"".equals(customRequestMapping.value())) {
            // 判断是否是以/开头的映射路径
            if (!customRequestMapping.value().startsWith("/")) {
              methodUrl.append("/");
            }
            // 添加方法的映射路径
            methodUrl.append(customRequestMapping.value());
            // 处理以/结尾的情况，去掉最后的/
            if (customRequestMapping.value().endsWith("/".trim())) {
              methodUrl.replace(methodControllerUrl.lastIndexOf("/"), methodControllerUrl.lastIndexOf("/"), "");
            }
          }
        }
        // 判断方法的url映射是否有效
        if (methodUrl.toString() != null
            && !"".equals(methodUrl.toString().replace("/", "").replace(" ", "").trim())) {
          // 拼接最后的URL
          methodControllerUrl.append(methodUrl.toString());
          // 做正则匹配的url
          String regularExpression = methodControllerUrl.toString().replaceAll("/+", "/");
          // 编译正则表达式
          Pattern pattern = Pattern.compile(regularExpression);

          // 添加到HandlerMapping中,做最后的检查，将多个连续的/换成一个
          handlerMapping.add(new Handler(entry.getValue(), method, pattern));
        } else {
          continue;
        }
      }
    }
    // 一个requestMapping会映射一个URL，这个路径或缓存起来
    for (Handler handler : handlerMapping) {
      System.out.println("HandlerMapping url : " + handler.getPattern());
    }
  }

  /**
   * 适配器（匹配的过程） 初始化HandlerAdapers 动态匹配参数，动态赋值
   *
   * @param context
   */
  private void initHandlerAdapters(HandlerApplicationContext context) {
    // 判断这个handler映射是否为空
    if (handlerMapping.isEmpty()) {
      return;
    }

    // 取出具体的映射的方法，并取出方法所有的参数
    for (Handler handler : handlerMapping) {

      // 方法形式参数名字为key，参数的索引号为value
      Map<String, Integer> paramMapping = new HashMap<>();
      // 方法注解参数名字作为value，形式参数名称作为value
      // 参数类型为key，参数的索引号为value
      Map<String, String> paramAnnotationMapping = new HashMap<>();

      // 取出映射的方法中的所有参数名称
      String[] parameters =
              MethodParameterNameUtils.getParameterNamesByAsm5(
                  handler.getController().getClass(), handler.getMethod());
      // 判断参数列表是否，如果为空则进行下一次遍历
      if (parameters == null || parameters.length == 0) {
        continue;
      }

      // 取出方法中每一个参数的类型
      Class<?>[] parameterTypes = handler.getMethod().getParameterTypes();
      // 循环遍历这个参数,这里的型参是有序的
      int i = 0;
      for (Class clazz : parameterTypes) {
        // 注入HttpServletRequest或者HttpServletResponse
        if (clazz == HttpServletRequest.class || clazz == HttpServletResponse.class) {
          paramMapping.put(parameters[i], i);
          i++;
          continue;
        }
        // 判断普通参数是否存在注解
        Annotation[][] annotations = handler.getMethod().getParameterAnnotations();
        if (annotations == null || annotations[i].length == 0) {
          // 按参数名字赋值
          paramMapping.put(parameters[i], i);
          i++;
          continue;
        } else {
          // 如果方法参数存在注解，就按注解赋值
          for (int j = i; j < annotations.length; j++) {
            // 遍历每一个形参的所有注解
            for (Annotation annotation : annotations[j]) {
              // 如果是requestParam，我们就进行处理
              if (annotation instanceof CustomRequestParam) {
                // 取出注解参数名称
                String value = ((CustomRequestParam) annotation).value();
                // 如果注解参数名称不为空，则按注解给定名称注入,如果为空就按字段类型名称
                if (value != null && !"".equals(value)) {
                  paramAnnotationMapping.put(parameters[i], value);
                  paramMapping.put(parameters[i], i);
                  i++;
                  break;
                } else {
                  paramMapping.put(parameters[i], i);
                  i++;
                  break;
                }
              }
            }
            break;
          }
        }
      }
      // 将适配器的的处理对象与参数列表对应起来
      adapterMap.add(new HandlerAdapter(paramMapping, paramAnnotationMapping, handler));
    }
  }

  /**
   * 异常解析
   *
   * @param context
   */
  private void initHandlerExceptionResolvers(HandlerApplicationContext context) {}

  /**
   * 视图转发（根据视图名字匹配到一个具体模板）
   *
   * @param context
   */
  private void initRequestToViewNameTranslator(HandlerApplicationContext context) {}

  /**
   * 解析模板中的内容（拿到服务器传过来的数据，生成HTML代码）
   * 初始化模板解析器包括对模板的语法检查，缓存内容
   * @param context
   */
  private void initViewResolvers(HandlerApplicationContext context) {

    //模板不会放在webapp下，而是放在WEB-INF下，或者classes下
    //避免直接请求带这个模板
    String template = context.getConfig().getProperty("template.root");
    //如果没有定义这个模板的路径，就使用默认的的路径
    if (template == null || !"".equals(template)) {
      //如果没有自定义这个模板存放的地址就使用默认的模板位置
      String filePath = this.getClass().getResource("/template").getFile();
      //获取模板的跟目录
      File rootFile = new File(filePath);
      //递归遍历所有的模板文件
      recursionViewResolvers(rootFile, rootFile.getPath());
    }
  }

  /**
   * 根据定义的文件跟路径去解析所有的模板
   *
   * @param rootFile
   */
  private void recursionViewResolvers(File rootFile, String rootPath) {
    //递归跟文件从跟文件起给文件路径命名
    for (File file : rootFile.listFiles()) {
      if (file.isDirectory()) {
        recursionViewResolvers(file, rootPath);
      } else {
        //替换掉文件目录的\\
        viewResolvers.add(new ViewResolver(file.getPath().replace(rootPath, "").replaceAll("\\\\", "/").replaceAll("//", "/"), file));
      }
    }
  }

  /** @param context */
  private void initFlashMapManager(HandlerApplicationContext context) {}

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // 设置响应内容类型
    resp.setContentType("text/html");
    resp.setCharacterEncoding("utf-8");
    this.doPost(req, resp);
  }

  /**
   * 调用自己写的controller方法
   *
   * @param req
   * @param resp
   * @throws IOException
   */
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    try {
      doDispatcher(req, resp);
    } catch (Exception e) {
      // resp.getWriter().write("500 Exception,Msg : " + Arrays.toString(e.getStackTrace()));
      resp.getWriter().write("500 Exception,Msg : " + e);
    }
  }

  /**
   * 获取handler处理器
   *
   * @param req
   * @return
   */
  private Handler getHandler(HttpServletRequest req) {
    // 循环HandlerMapping，取出Handler
    if (handlerMapping.isEmpty()) {
      return null;
    }

    String url = req.getRequestURI();
    String contextPath = req.getContextPath();
    url = url.replace(contextPath, "").replaceAll("/+", "/");
    System.out.println(url);
    /** return handlerMapping.get(url); */

    // 正则匹配URL
    for (Handler handler : handlerMapping) {
      // 获取匹配结果
      Matcher matcher = handler.getPattern().matcher(url);
      // 没匹配到对应的url，就不处理，接着向下处理
      if (!matcher.matches()) {
        continue;
      }
      // 找到相应的处理handler
      return handler;
    }
    return null;
  }

  /**
   * 获取handler的适配器
   *
   * @param handler
   * @return
   */
  private HandlerAdapter getHandlerAdapter(Handler handler) {
    if (adapterMap.isEmpty()) {
      return null;
    }
    for (HandlerAdapter handlerAdapter : adapterMap) {
      if (handler == handlerAdapter.getHandler()) {
        return handlerAdapter;
      }
    }
    return null;
  }

  /**
   * 核心处理方法
   *
   * @param req
   * @param resp
   */
  private void doDispatcher(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    // 先取出来一个Handler，从HandlerMapping中取
    Handler handler = getHandler(req);
    //获取网络输出流
    PrintWriter writer=null;
    try {
      if (handler == null) {
        resp.getWriter().write("404 Not Found");
        return;
      }
      // 由Handler取出适配器，由适配器去做处理我们具体的方法
      HandlerAdapter handlerAdapter = getHandlerAdapter(handler);
      // 由适配器的Handler去处理方法
      Object result = handlerAdapter.handle(req, resp);
      //返回的对象是ModelAndView就需要解析
      if (result.getClass() == ModelAndView.class) {
        ModelAndView modelAndView = (ModelAndView) result;
        /**
         * magicLuo模板框架
         * Velocity #{}
         * Freemark #{}
         * jsp ${}
         * magicLuo #{}
         * spring利用这个方法向外输入model
         */
        applyDefaultViewName(resp, modelAndView);
      } else if (result != null) {
        //获取输入流
        writer = resp.getWriter();
        writer.write(result.toString());
      } else {
        writer = resp.getWriter();
        writer.write("The request has been successfully responded");
      }
    } catch (Exception e) {
      if (e.getClass() == IllegalArgumentException.class) {
        throw new CustomException(
                handler.getMethod().getName()
                        + ",the method required params can not be empty,Please check",
                e);
      } else {
        throw e;
      }
    } finally {
      try {
        if (writer != null) {
          writer.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 按默认的视图解析
   *
   * @param resp
   * @param modelAndView
   */
  private void applyDefaultViewName(HttpServletResponse resp, ModelAndView modelAndView) throws Exception {
    //如果没有这个model对象
    if (modelAndView == null || viewResolvers.isEmpty()) {
      return;
    }
    //输出器
    ServletOutputStream outputStream = null;
    //循环视图解析器
    for (ViewResolver vr : viewResolvers) {
      //这次没有查询到这个视图解析器就继续向下执行
      if (!modelAndView.getView().equals(vr.getViewName())) {
        continue;
      }
      //查询到了之后，就真正利用正则表达式进行解析
      String parse = vr.parse(modelAndView);
      if (parse != null) {
        //输出
        try {
          outputStream = resp.getOutputStream();
          outputStream.write(parse.getBytes(CharsetEncodingFilter.CHARSETENCODING));
          return;
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          if (outputStream != null) {
            outputStream.close();
          }
        }
      }
    }
    //循环完之后还是没有找到对应的视图就提示找不到对应的视图
    try {
      outputStream = resp.getOutputStream();
      outputStream.write("404 Not Found".getBytes(CharsetEncodingFilter.CHARSETENCODING));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (outputStream != null) {
        outputStream.close();
      }
    }
  }
}
