package com.lcydream.project.config;

import com.lcydream.project.start.entity.Member;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * MapperRegistory
 *
 * @author Luo Chun Yun
 * @date 2018/8/5 20:13
 */
public class MapperRegistory {

    public static final Map<String, MapperData> methodSqlMapping = new HashMap<>();

    public MapperRegistory() {
    }

    /**
     * 使用 1. 注册mapper信息
     *     2. Java Bean的属性名字要和数据库表中的列名字一致
     * @param sql 执行的sql
     * @param clazz 返回的结果对象
     */
    public MapperRegistory(String namespace, String sql, Class<?> clazz, Method method) {
        /*methodSqlMapping.put("com.lcydream.project.mybatis.my.MemberMapper.selectByPrimaryKey",
                new MapperData("select * from test where id = %d", Member.class));*/
        methodSqlMapping.put(namespace,
                new MapperData(sql, clazz,method));
    }

    public void setMethodSqlMapping(String namespace,String sql,Class<?> clazz,Method method){
        methodSqlMapping.put(namespace,
                new MapperData(sql, clazz,method));
    }

    public static Map<String, MapperData> getMethodSqlMapping() {
        return methodSqlMapping;
    }

    /**
     * 构造查询结果集合
     * @param <T> 映射类型
     */
    public class MapperData<T>{
        private String sql;
        private Class<T> type;
        private Method method;

        public MapperData(String sql, Class<T> type,Method method) {
            this.sql = sql;
            this.type = type;
            this.method = method;
        }

        public String getSql() {
            return sql;
        }

        public void setSql(String sql) {
            this.sql = sql;
        }

        public Class<T> getType() {
            return type;
        }

        public void setType(Class<T> type) {
            this.type = type;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }
    }

    public MapperData get(String nameSpace) {
        return methodSqlMapping.get(nameSpace);
    }
}
