/*
package com.lcydream.project.utils;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import jdk.internal.org.objectweb.asm.*;

*/
/**
 * MethodParameterNameUtils 根据某个类的某个方法获取，方法的参数名称
 *
 * @author Luo Chun Yun
 * @date 2018/7/21 11:04
 *//*

public class MethodParameterNameUtils {

  */
/**
   * 获取指定类指定方法的参数名
   *
   * @param clazz 要获取参数名的方法所属的类
   * @param method 要获取参数名的方法
   * @return 按参数顺序排列的参数名列表，如果没有参数，则返回null
   *//*

  public static String[] getMethodParameterNamesByAsm4(Class<?> clazz, final Method method) {
    final Class<?>[] parameterTypes = method.getParameterTypes();
    if (parameterTypes == null || parameterTypes.length == 0) {
      return null;
    }
    final Type[] types = new Type[parameterTypes.length];
    for (int i = 0; i < parameterTypes.length; i++) {
      types[i] = Type.getType(parameterTypes[i]);
    }
    final String[] parameterNames = new String[parameterTypes.length];

    String className = clazz.getName();
    int lastDotIndex = className.lastIndexOf(".");
    className = className.substring(lastDotIndex + 1) + ".class";
    InputStream is = clazz.getResourceAsStream(className);
    try {
      ClassReader classReader = new ClassReader(is);
      classReader.accept(
          new ClassVisitor(Opcodes.ASM4) {
            @Override
            public MethodVisitor visitMethod(
                int access, String name, String desc, String signature, String[] exceptions) {
              // 只处理指定的方法
              Type[] argumentTypes = Type.getArgumentTypes(desc);
              if (!method.getName().equals(name) || !Arrays.equals(argumentTypes, types)) {
                return null;
              }
              return new MethodVisitor(Opcodes.ASM4) {
                @Override
                public void visitLocalVariable(
                    String name, String desc, String signature, Label start, Label end, int index) {
                  // 静态方法第一个参数就是方法的参数，如果是实例方法，第一个参数是this
                  if (Modifier.isStatic(method.getModifiers())) {
                    parameterNames[index] = name;
                  } else if (index > 0) {
                    parameterNames[index - 1] = name;
                  }
                }
              };
            }
          },
          0);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (is != null) {
          is.close();
        }
      } catch (Exception e2) {
        e2.printStackTrace();
      }
      return parameterNames;
    }
  }

  */
/**
   * 获取指定类指定方法的参数名
   *
   * @param clazz 要获取参数名的方法所属的类
   * @param method 要获取参数名的方法
   * @return 按参数顺序排列的参数名列表，如果没有参数，则返回null
   *//*

  public static String[] getParameterNamesByAsm5(Class<?> clazz, final Method method) {
    final Class<?>[] parameterTypes = method.getParameterTypes();
    if (parameterTypes == null || parameterTypes.length == 0) {
      return null;
    }
    final Type[] types = new Type[parameterTypes.length];
    for (int i = 0; i < parameterTypes.length; i++) {
      types[i] = Type.getType(parameterTypes[i]);
    }
    final String[] parameterNames = new String[parameterTypes.length];

    String className = clazz.getName();
    int lastDotIndex = className.lastIndexOf(".");
    className = className.substring(lastDotIndex + 1) + ".class";
    InputStream is = clazz.getResourceAsStream(className);
    try {
      ClassReader classReader = new ClassReader(is);
      classReader.accept(
          new ClassVisitor(Opcodes.ASM5) {
            @Override
            public MethodVisitor visitMethod(
                int access, String name, String desc, String signature, String[] exceptions) {
              // 只处理指定的方法
              Type[] argumentTypes = Type.getArgumentTypes(desc);
              if (!method.getName().equals(name) || !Arrays.equals(argumentTypes, types)) {
                return super.visitMethod(access, name, desc, signature, exceptions);
              }
              return new MethodVisitor(Opcodes.ASM5) {
                @Override
                public void visitLocalVariable(
                    String name, String desc, String signature, Label start, Label end, int index) {
                  // 非静态成员方法的第一个参数是this
                  if (Modifier.isStatic(method.getModifiers())) {
                    parameterNames[index] = name;
                  } else if (index > 0) {
                    parameterNames[index - 1] = name;
                  }
                }
              };
            }
          },
          0);
      return parameterNames;
    } catch (IOException e) {
    } finally {
      try {
        if (is != null) {
          is.close();
        }
      } catch (Exception e2) {
        e2.printStackTrace();
      }
      return parameterNames;
    }
  }

  */
/**
   * 根据clazz的类型进行类型装换
   *
   * @param value 值
   * @param clazz 类型
   * @return
   *//*

  public static Object castValue(String value, Class clazz) {
    if (clazz == String.class) {
      return toString(value, null);
    } else if (clazz == Integer.class) {
      return toInt(value, null);
    } else if (clazz == int.class) {
      return toInt(value, null);
    } else if (clazz == Long.class) {
      return toLong(value, null);
    } else if (clazz == long.class) {
      return toLong(value, null);
    } else if (clazz == Float.class) {
      return toFloat(value, null);
    } else if (clazz == float.class) {
      return toFloat(value, null);
    } else if (clazz == Double.class) {
      return toDouble(value, null);
    } else if (clazz == double.class) {
      return toDouble(value, null);
    }
    return null;
  }

  */
/**
   * <将obj转换为string，如果obj为null则返回defaultVal>
   *
   * @param obj 需要转换为string的对象
   * @param defaultVal 默认值
   * @return obj转换为string
   *//*

  public static String toString(Object obj, String defaultVal) {
    return (obj != null) ? obj.toString() : defaultVal;
  }
  */
/**
   * <将obj转换为string，默认为空>
   *
   * @param obj 需要转换为string的对象
   * @return 将对象转换为string的字符串
   *//*

  public static String toString(Object obj) {
    return toString(obj, "");
  }

  */
/**
   * <将对象转换为int>
   *
   * @param obj 需要转换为int的对象
   * @param defaultVal 默认值
   * @return obj转换成的int值
   *//*

  public static Integer toInt(Object obj, Integer defaultVal) {
    try {
      return (obj != null) ? Integer.parseInt(toString(obj, "0")) : defaultVal;
    } catch (Exception e) {
    }
    return defaultVal;
  }

  */
/**
   * <将对象转换为int>
   *
   * @param obj 需要转换为int的对象
   * @return obj转换成的int值
   *//*

  public static Integer toInt(Object obj) {
    return toInt(obj, 0);
  }

  */
/**
   * <将对象转换为Integer>
   *
   * @param obj 需要转换为Integer的对象
   * @return obj转换成的Integer值
   *//*

  public static Integer toInteger(Object obj) {
    return toInt(obj, null);
  }

  */
/**
   * <将对象转换为int>
   *
   * @param obj 需要转换为int的对象
   * @param defaultVal 默认值
   * @return obj转换成的int值
   *//*

  public static Float toFloat(Object obj, Float defaultVal) {
    return (obj != null) ? Float.parseFloat(toString(obj, "0")) : defaultVal;
  }

  */
/**
   * <将对象转换为Float>
   *
   * @param obj 需要转换为Float的对象
   * @return obj转换成的Float值
   *//*

  public static Float toFloat(Object obj) {
    return toFloat(obj, 0F);
  }

  */
/**
   * <将obj转换为long>
   *
   * @param obj 需要转换的对象
   * @param defaultVal 默认值
   * @return 如果obj为空则返回默认，不为空则返回转换后的long结果
   *//*

  public static Long toLong(Object obj, Long defaultVal) {
    return (obj != null) ? Long.parseLong(toString(obj)) : defaultVal;
  }

  */
/**
   * <将obj转换为long>
   *
   * @param obj 需要转换的对象
   * @return 如果obj为空则返回默认的0l，不为空则返回转换后的long结果
   *//*

  public static Long toLong(Object obj) {
    return toLong(obj, 0L);
  }

  */
/**
   * 将object转换为double类型，如果出错则返回 defaultVal
   *
   * @param obj 需要转换的对象
   * @param defaultVal 默认值
   * @return 转换后的结果
   *//*

  public static Double toDouble(Object obj, Double defaultVal) {
    try {
      return Double.parseDouble(obj.toString());
    } catch (Exception e) {
      return defaultVal;
    }
  }

  */
/**
   * 将object转换为double类型，如果出错则返回 0d
   *
   * @param obj 需要转换的对象
   * @return 转换后的结果
   *//*

  public static double toDouble(Object obj) {
    return toDouble(obj, 0d);
  }

  */
/**
   * <将List<Object>转换为List<Map<String, Object>>>
   *
   * @param list 需要转换的list
   * @return 转换的结果
   *//*

  public static List<Map<String, Object>> converterForMapList(List<Object> list) {
    List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    for (Object tempObj : list) {
      result.add((HashMap<String, Object>) tempObj);
    }
    return result;
  }
}
*/
