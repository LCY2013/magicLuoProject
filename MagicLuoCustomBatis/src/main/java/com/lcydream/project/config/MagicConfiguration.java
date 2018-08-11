package com.lcydream.project.config;

import com.lcydream.project.annotation.Sql;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * MagicConfiguration
 *
 * @author Luo Chun Yun
 * @date 2018/8/5 14:57
 */
@Data
public class MagicConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(MagicConfiguration.class);

    private String scanPath;

    List<String> classCache = new ArrayList<>();

    private MapperRegistory mapperRegistory = new MapperRegistory();

    public MagicConfiguration scanPath(String scanPath) {
        this.scanPath = scanPath;
        return this;
    }


    public void build() throws Exception {
        if (null == scanPath || scanPath.length() < 1) {
            throw new RuntimeException("scan path is required .");
        }
        //扫描注册mapper
        doRegister(scanPath);
        //查看mapper是否存在
        if(classCache.size() == 0){
            logger.warn("-------------没有mapper被找到---------------");
            return;
        }
        //开始注册所有的mapper信息
        for(String classPath : classCache){
            // 反射出类对象
            Class<?> clazz = Class.forName(classPath);
            try {
                if(!clazz.isInterface() || !clazz.getSimpleName().matches(".+?Mapper")){
                    logger.info(clazz.getSimpleName()+"不满足Mapper规范");
                    continue;
                    //throw new RuntimeException("--------------"+classPath+"不是接口，不满足mapper规范-------------");
                }
            }catch (Exception e){
                continue;
            }
            Method[] declaredMethods = clazz.getDeclaredMethods();
            for(Method method : declaredMethods) {
                method.setAccessible(true);
                if (method.isAnnotationPresent(Sql.class)) {
                    Sql sql = method.getAnnotation(Sql.class);
                    if (StringUtils.isNotBlank(sql.sql())) {
                        mapperRegistory.setMethodSqlMapping(
                                classPath+"."+method.getName(), sql.sql(), clazz,method);
                    }
                }
            }
        }
    }

    /**
     * 把所有符合条件的class全部找出来，存起来放入classCache中
     *
     * @param packageName
     */
    private void doRegister(String packageName) {
        String url = this.getClass().getResource("/").getPath();
        if(StringUtils.isNotBlank(packageName)){
            url += packageName.replaceAll("\\.", "/");
        }
        File dir = new File(url);
        for (File file : dir.listFiles()) {
            // 如果是一个文件夹就递归所有的class文件
            if (file.isDirectory()) {
                // 当没有定义扫描包时，就直接获取该文件夹名称
                if (packageName != null && "".equals(packageName)) {
                    doRegister(file.getName());
                } else {
                    doRegister(packageName + ".".toLowerCase() + file.getName());
                }
            } else {
                if (file.getName().endsWith(".class")) {
                    classCache.add(packageName + "." + file.getName().replace(".class", "").trim());
                }
            }
        }
    }

    public MapperRegistory getMapperRegistory() {
        return mapperRegistory;
    }
}
