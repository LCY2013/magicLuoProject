package com.lcydream.project.designmode.proxy.custom;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * LuoProxy
 * 生成代理对象的代码
 * @author Luo Chun Yun
 * @date 2018/6/19 21:15
 */
public class LuoProxy {

    private static String ln="\r\n";

    public static Object newProxyInstance(LuoClassLoader loader,
                                          Class<?>[] interfaces,
                                          LuoInvocationHandler h)
            throws IllegalArgumentException,Exception{
        //1、生成源代码
        String proxySrc = generateSrc(interfaces);
        //2、将生成的源代码输出到磁盘，保存为.java文件
        String filePath = LuoProxy.class.getResource("").getPath();
        File f = new File(filePath + "$Proxy0.java");
        FileWriter fw = new FileWriter(f);
        fw.write(proxySrc);
        fw.flush();
        fw.close();
        //3、编译源代码，并且生成.class文件
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> javaFileObjects = standardFileManager.getJavaFileObjects(f);

        JavaCompiler.CompilationTask task = compiler.getTask(null, standardFileManager, null, null, null, javaFileObjects);
        task.call();
        standardFileManager.close();
        //4、将class文件中的内容，动态加载到jvm中
        //5、返回被代理后的代理对象
        Class<?> proxy0 = loader.findClass("$Proxy0");
        Constructor<?> constructor = proxy0.getConstructor(LuoInvocationHandler.class);
        //这里可以删除动态生成的源代码
        f.delete();
        return constructor.newInstance(h);
    }

    private static String generateSrc(Class<?>[] interfaces){
        StringBuffer src = new StringBuffer();
        src.append("package com.lcydream.project.designmode.proxy.custom;"+ln);
        src.append("import java.lang.reflect.Method;"+ln);
        src.append("public class $Proxy0");
        for (Class clazz : interfaces){
            src.append(" implements "+clazz.getName());
        }
        src.append("{"+ln);
        src.append("LuoInvocationHandler h;"+ln);
        src.append("public $Proxy0(LuoInvocationHandler h){"+ln);
        src.append("this.h = h;" + ln);
        src.append("}"+ln);

        for(Class clazz : interfaces){
            for(Method m : clazz.getMethods()){
                src.append("public "+m.getReturnType().getName() + " "+m.getName() + "(");
                int i = 0;
                for(Parameter p : m.getParameters()){
                    if(i < m.getParameters().length){
                        src.append(p.getType()+" " + p.getName()+",");
                    }else {
                        src.append(p.getType()+" " + p.getName());
                    }
                    i++;
                }
                src.append("){"+ln);
                src.append("try{"+ln);
                src.append("Method m = " + clazz.getName() + ".class.getMethod(\""+m.getName()+"\",new Class[]{});"+ln);
                src.append("this.h.invoke(this,m,null);"+ln);
                src.append("}catch(RuntimeException | Error var2){throw var2;}" +
                        "catch(Throwable e){e.printStackTrace();}");
                if(!m.getReturnType().getName().endsWith("void")){
                    src.append("return null;"+ln);
                }
                src.append("}"+ln);
            }
        }

        src.append("}");

        return src.toString();
    }
}
