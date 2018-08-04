package com.lcydream.project.designmode.proxy.custom;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * LuoClassLoader
 *  代码生成、编译、动态重新load到jvm中
 * @author Luo Chun Yun
 * @date 2018/6/19 21:20
 */
public class LuoClassLoader extends ClassLoader{

    private File baseDir;

    public LuoClassLoader(){
        String basePath = LuoClassLoader.class.getResource("").getPath();
        this.baseDir = new File(basePath);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String className = LuoClassLoader.class.getPackage().getName()+"."+name;
        if(baseDir != null){
            File classFile = new File(baseDir,name.replace("\\.","/")+".class");
            if(classFile.exists()){
                FileInputStream in = null;
                ByteArrayOutputStream out = null;
                try{
                    in = new FileInputStream(classFile);
                    out = new ByteArrayOutputStream();
                    byte[] buff = new byte[1024];
                    int len;
                    while ((len = in.read(buff))!=-1){
                        out.write(buff,0,len);
                    }
                    return defineClass(className,out.toByteArray(),0,out.size());
                }catch (Exception e){
                    e.printStackTrace();
                }finally{
                    try{
                        if (null != in) {
                          in.close();
                        }
                        if (null != out) {
                            out.close();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    //最后删除生成的字节码
                    classFile.delete();
                }
            }
        }
        return super.findClass(name);
    }

}
