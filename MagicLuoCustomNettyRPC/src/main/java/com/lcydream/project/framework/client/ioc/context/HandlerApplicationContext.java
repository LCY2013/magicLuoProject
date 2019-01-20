package com.lcydream.project.framework.client.ioc.context;

import com.lcydream.project.framework.client.ioc.annotation.CustomAwtowired;
import com.lcydream.project.framework.client.ioc.annotation.CustomComtroller;
import com.lcydream.project.framework.client.ioc.annotation.CustomService;
import com.lcydream.project.framework.client.ioc.utils.StringUtils;
import com.lcydream.project.framework.client.proxy.RpcProxy;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * HandlerApplicationContext
 *
 * @author Luo Chun Yun
 * @date 2018/7/16 22:32
 */
public final class HandlerApplicationContext {

  private Map<String, Object> instanceMapping = new ConcurrentHashMap<String, Object>();

  /** 类似于内部的配置信息，我们在外面是看不见的 我们能够看见的只有IOC容器提供的getBean方法来间接调用 */
  private List<String> classCache = new ArrayList<>();
  /** 做接口的类型匹配 */
  private List<Class> interfaceCache = new ArrayList<>();
  /** 普通类缓存 */
  private List<Class> classNoAnnotation = new ArrayList<>();
  /**
   * 定位配置文件
   */
  Properties config = new Properties();

  public HandlerApplicationContext(String location) {
    // 先加载配置文件
    // 定位，载入，注册，初始化，注入
    InputStream is = null;
    try {
      // 定位
      is = this.getClass().getClassLoader().getResourceAsStream(location);
      if (is != null) {
        config.load(is);
      }
      // 注册，把所有的class找出来缓存
      String packageName = config.getProperty("client.scan.package");
      // 不存在扫描包的文件，这里就可以直接返回了
      if (null == packageName || "".equalsIgnoreCase(packageName)) {
        // throw new RuntimeException("没有找到对应的扫描包");
        // return;
        packageName = "";
      }
      // 注册配置Bean信息
      doRegister(packageName);

      // 初始化
      doCreateBean();

      // 初始化有注解的接口，实现接口的自动注入
      doAutoWiredInterfaceClass();

      // 注入
      populater();

      // 注销接口和普通bean的缓存
      interfaceCache = null;
      classNoAnnotation = null;
      System.out.println("MagicLuoIOC <==================> 容器初始化完成");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (null != is) {
          is.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 把所有符合条件的class全部找出来，存起来放入classCache中
   *
   * @param packageName
   */
  private void doRegister(String packageName) {
    URL url;
    if (packageName != null && !"".equals(packageName.trim())) {
      url = this.getClass().getResource("/" + packageName.replaceAll("\\.", "/"));
    } else {
      url = this.getClass().getResource("/");
    }
    File dir = new File(url.getFile());
    for (File file : dir.listFiles()) {
      // 如果是一个文件夹就递归所有的class文件
      if (file.isDirectory()) {
        // 当没有定义扫描包时，就直接获取该文件夹名称
        if (packageName != null && "".equals(packageName)) {
          doRegister(file.getName());
        } else {
          doRegister(packageName + "." + file.getName());
        }
      } else {
        if (file.getName().endsWith(".class")) {
          classCache.add(packageName + "." + file.getName().replace(".class", "").trim());
        }
      }
    }
  }

  /** 初始化Bean */
  private void doCreateBean() {
    /**
     * 检查是否有注册信息，注册信息包括了扫描包下面的所有class名字
     * Spring中BeanDefinition也保存了类的名字，也保存类与类之间的关系(Map/list/Set/ref/parent)等信息
     */
    if (classCache.size() == 0) {
      return;
    }
    try {
      for (String className : classCache) {
        // 反射出类对象,这里有一个套路，在Spring中会判断时候有接口，从而选择JDK的动态代理，还是CGLib的动态代理
        Class<?> clazz = Class.forName(className);
        // 那个类不要初始化，那个类不需要初始化
        // 只要加载@service，@controller等等需要初始化
        if (clazz.isAnnotationPresent(CustomComtroller.class)) {
          if (clazz.isInterface()) {
            // 注解类是接口的话，不做初始化操作
            continue;
          }
          // 命名格式定义为类名首字符小写
          instanceMapping.put(
              StringUtils.lowerFirstCharForString(clazz.getSimpleName()), clazz.newInstance());
        } else if (clazz.isAnnotationPresent(CustomService.class)) {
          // 如果是空，就用默认规则
          // 1、类名首字符小写
          // 如果这个类是接口
          // 2、可以根据类型匹配
          if (clazz.isInterface()) {
            // 注解类是接口的话，不做初始化操作，先将其放入接口缓存中做类型匹配
            interfaceCache.add(clazz);
            continue;
          }
          CustomService annotation = clazz.getAnnotation(CustomService.class);
          // 如果设置了自定义名称，就优先使用自定义名称
          String serverName = annotation.value();
          if (!"".equalsIgnoreCase(serverName)) {
            instanceMapping.put(serverName, clazz.newInstance());
            continue;
          }
          Class<?>[] interfaces = clazz.getInterfaces();
          if (interfaces.length > 0) {
            // 如果这个类实现了接口
            for (Class clazzs : interfaces) {
              instanceMapping.put(
                  StringUtils.lowerFirstCharForString(clazzs.getSimpleName()), clazz.newInstance());
            }
          } else {
            instanceMapping.put(
                StringUtils.lowerFirstCharForString(clazz.getSimpleName()), clazz.newInstance());
          }

        } else {
          // 如果是一个没有注解的class
          if (clazz.isInterface()) {
            // 如果是普通接口的话，不做初始化操作
            continue;
          } else {
            // 如果是普通类的话进入普通类缓存
            classNoAnnotation.add(clazz);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /** 实现接口类型的实例化 */
  private void doAutoWiredInterfaceClass() throws Exception {
    // 不存在接口的注解类，直接返回
    if (interfaceCache.size() <= 0) {
      return;
    }
    /** 给带注解的接口实现注入 */
    for (Class clazz : interfaceCache) {
      CustomService annotation = (CustomService) clazz.getAnnotation(CustomService.class);
      // 如果设置了自定义名称，就优先使用自定义名称
      String serverName = annotation.value();
      int i = 0;
      Class instanceClazz = null;
      // 遍历未实现注解的类，找到接口的实现类，如果存在多个就不做初始化，并抛出异常
      for (Class clazzs : classNoAnnotation) {
        // 获取普通实现类的接口情况
        Class[] interfaces = clazzs.getInterfaces();
        // 判读是否实现了改注解接口
        for (Class interfaced : interfaces) {
          if (interfaced == clazz) {
            i++;
            instanceClazz = clazzs;
            break;
          }
        }
        // 如果注解接口存在多个实现类，就不允许初始化这个框架
        if (i > 1) {
          new RuntimeException("找到" + clazz.getName() + "多个实现类，不符合框架的要求");
          System.exit(1);
        }
      }
      if (instanceClazz != null) {
        // 判断实现的注解接口是否存在注解名称，存在就使用注解名称，不存在就用注解类的首字符小写
        if (serverName != null && !"".equals(serverName)) {
          instanceMapping.put(serverName, instanceClazz.newInstance());
        } else {
          instanceMapping.put(
              StringUtils.lowerFirstCharForString(clazz.getSimpleName()),
              instanceClazz.newInstance());
        }
      }
    }
  }

  /** 注入 */
  private void populater() {

    // 首先判断IOC容器中是否存在bean
    if (instanceMapping.isEmpty()) {
      return;
    }

    for (Map.Entry<String, Object> entry : instanceMapping.entrySet()) {

      // 取出所有的类属性
      Field[] declaredFields = entry.getValue().getClass().getDeclaredFields();
      for (Field field : declaredFields) {
        // 判断这个属性是否需要自动注入
        if (!field.isAnnotationPresent(CustomAwtowired.class)) {
          continue;
        }
        // 如果需要自动注入
        CustomAwtowired awtowired = field.getAnnotation(CustomAwtowired.class);
        // 设置私有属性的访问权限
        field.setAccessible(true);
        try {
          //判断这个属性时候是接口，如果是接口就启用rpc调用
          if(field.getType().isInterface()){
            // 如果注入的属性名称为空，我们默认使用类型注入，不为空就根据名字注入
            if ("".equals(awtowired.value())) {
              //如果没有设置注入类的名称，默认约定为类名首字母小写的驼峰命名
              String serviceName = StringUtils.lowerFirstCharForString(field.getType().getSimpleName());
              Object obj = RpcProxy.create(field.getType(), serviceName);
              field.set(entry.getValue(), obj);
            }else {
              //获取注入类的信息
              String serviceName = awtowired.value();
              Object obj = RpcProxy.create(field.getType(), serviceName);
              field.set(entry.getValue(), obj);
            }
          }else {
            // 如果注入的属性名称为空，我们默认使用类型注入，不为空就根据名字注入
            if ("".equals(awtowired.value())) {
              for (Object obj : instanceMapping.values()) {
                // 判断该类是否实现了接口
                if (field.getType().isAssignableFrom(obj.getClass())) {
                  // 获取当前容器中的对象，并且初始化它的值
                  field.set(entry.getValue(), obj);
                } else
                  // 判断是否是类的注入
                  if (obj.getClass().getSimpleName() == field.getClass().getSimpleName()) {
                    // 获取当前容器中的对象，并且初始化它的值
                    field.set(entry.getValue(), obj);
                  } else
                  // 如果没有找到该字段的引用
                  {
                    // 如果IOC容器中不存在
                    Logger.getGlobal()
                            .log(
                                    Level.WARNING,
                                    "在"
                                            + entry.getValue().getClass()
                                            + "没找到名称为"
                                            + StringUtils.lowerFirstCharForString(awtowired.value())
                                            + "中"
                                            + field.getName()
                                            + "的对象，可能没有在容器中");
                  }
              }
            } else {
              // 如果容器中存在需要初始化的对象的引用就直接从IOC容器中取出赋值
              if (instanceMapping.get(StringUtils.lowerFirstCharForString(awtowired.value()))
                      != null) {
                // 获取当前容器中的对象，并且初始化它的值
                field.set(
                        entry.getValue(),
                        instanceMapping.get(StringUtils.lowerFirstCharForString(awtowired.value())));
              } else {
                // 如果IOC容器中不存在
                Logger.getGlobal()
                        .log(
                                Level.WARNING,
                                "在"
                                        + entry.getValue().getClass()
                                        + "没有找到名称为"
                                        + StringUtils.lowerFirstCharForString(awtowired.value())
                                        + "中"
                                        + field.getName()
                                        + "的对象，可能没有在容器中");
              }
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * 根据bean的名字获取IOC容器中的Bean
   *
   * @param beanName
   * @return
   */
  public Object getBean(String beanName) {
    if (interfaceCache != null || classNoAnnotation != null) {
      System.out.println("容器正在初始化...");
      return null;
    }
    return instanceMapping.get(beanName);
  }

  /**
   * 根据类型获取IOC容器中的Bean
   *
   * @param clazz
   * @return
   */
  public Object getBean(Class clazz) {
    if (interfaceCache != null || classNoAnnotation != null) {
      System.out.println("容器正在初始化...");
      return null;
    }
    for (Object obj : instanceMapping.values()) {
      // 判断获取的bean对象是否是接口
      if (clazz.isAssignableFrom(obj.getClass())) {
        return obj;
      } else
      // 判断是否是类的
      if (obj.getClass().getSimpleName() == clazz.getSimpleName()) {
        return obj;
      }
    }
    return instanceMapping.get(StringUtils.lowerFirstCharForString(clazz.getSimpleName()));
  }

  /**
   * 获取IOC容器
   *
   * @return
   */
  public Map getAll() {
    return instanceMapping;
  }

  /**
   * 获取配置文件
   *
   * @return
   */
  public Properties getConfig() {
    return config;
  }
}
