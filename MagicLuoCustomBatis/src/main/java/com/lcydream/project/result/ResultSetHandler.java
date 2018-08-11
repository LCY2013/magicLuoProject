package com.lcydream.project.result;

import com.alibaba.fastjson.JSON;
import com.lcydream.project.config.MagicConfiguration;
import com.lcydream.project.config.MapperRegistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ResultSetHandler
 *
 * @author Luo Chun Yun
 * @date 2018/8/5 21:49
 */
public class ResultSetHandler {

    private Logger logger = LoggerFactory.getLogger(ResultSetHandler.class);
    private MagicConfiguration magicConfiguration;

    public <E> E handle(PreparedStatement pstmt, MapperRegistory.MapperData mapperData) throws SQLException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        try {
            ResultSet rs = pstmt.getResultSet();
            if (mapperData.getMethod().getReturnType() == Map.class || mapperData.getMethod().getReturnType().isAssignableFrom(Map.class) ) {
                Map map = createMapResult(rs);
                return (E)map;
            }else if(mapperData.getMethod().getReturnType() == List.class || mapperData.getMethod().getReturnType().isAssignableFrom(List.class) ){
                Type[] classReturnValType = getClassReturnValType(mapperData.getMethod());
                List list = new ArrayList();
                if(classReturnValType[0] == Map.class || classReturnValType[0] instanceof Map
                    || classReturnValType[0].equals(Map.class)
                    || classReturnValType[0].toString().replaceAll("<.*>","")
                        .equals("java.util.Map")){
                    while (rs.next()){
                        Map mapResult = createListMapResult(rs);
                        list.add(mapResult);
                    }
                    return (E)list;
                }else if(classReturnValType!=null && classReturnValType.length>0
                        && classReturnValType[0].getClass().equals(String.class)){
                    while (rs.next()){
                        String stringResult = createStringResult(rs);
                        list.add(stringResult);
                    }
                    return (E)list;
                }else if(classReturnValType!=null && classReturnValType.length>0
                        && (classReturnValType[0] == Integer.class || classReturnValType[0] == int.class)){
                    while (rs.next()){
                        Integer intResult = createIntResult(rs);
                        list.add(intResult);
                    }
                    return (E)list;
                }else {
                    //获取实体对象
                    Class<?> aClass = Class.forName(mapperData.getClass().getName());
                    while (rs.next()) {
                        Object resultVal = aClass.newInstance();
                        for (Field field : resultVal.getClass().getDeclaredFields()) {
                            try {
                                setValue(resultVal, field, rs);
                            }catch (Exception e){}
                        }
                        list.add(resultVal);
                    }
                    return (E)list;
                }

            }else {
                //获取实体对象
                Class<?> aClass = Class.forName(mapperData.getMethod().getReturnType().getName());
                Object resultVal = aClass.newInstance();
                if (rs.next()) {
                    for (Field field : resultVal.getClass().getDeclaredFields()) {
                        setValue(resultVal, field, rs);
                    }
                }
                return (E)resultVal;
            }

        }catch (Exception e){
            logger.info(e.getLocalizedMessage());
        }
        return null;
    }

    private Map createMapResult(ResultSet rs) throws Exception{
        rs.last();
        if(rs.getRow()!=1){
            throw new RuntimeException("返回的结果集不是唯一");
        }
        rs.first();
        Map map = new HashMap(20);
        //获取元数据
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        for(int i=1;i<=columnCount;i++) {
            map.put(metaData.getColumnName(i),rs.getObject(i));
        }
        return map;
    }

    private Map createListMapResult(ResultSet rs) throws Exception{
        Map map = new HashMap(20);
        //获取元数据
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        for(int i=1;i<=columnCount;i++) {
            map.put(metaData.getColumnName(i),rs.getObject(i));
        }
        return map;
    }

    private String createStringResult(ResultSet rs) throws Exception{
        //获取元数据
        ResultSetMetaData metaData = rs.getMetaData();
        if(metaData.getColumnCount() != 1){
            throw new RuntimeException("返回的结果集不是唯一");
        }
        return rs.getString(1);
    }

    private int createIntResult(ResultSet rs) throws Exception{
        //获取元数据
        ResultSetMetaData metaData = rs.getMetaData();
        if(metaData.getColumnCount() != 1){
            throw new RuntimeException("返回的结果集不是唯一");
        }
        return rs.getInt(1);
    }

    private void setValue(Object resultObj, Field field, ResultSet rs) throws NoSuchMethodException, SQLException, InvocationTargetException, IllegalAccessException {
        if(rs.getRow()!=1){
            throw new RuntimeException("返回的结果集不是唯一");
        }
        Method setMethod = resultObj.getClass().getMethod("set" + upperCapital(field.getName()), field.getType());
        setMethod.invoke(resultObj, getResult(field,rs));
    }

    private Object getResult(Field field, ResultSet rs) throws SQLException {
        //TODO type handles
        Class<?> type = field.getType();
        if(Integer.class == type || int.class == type){
            return rs.getInt(field.getName());
        }
        if(Long.class == type || long.class == type){
            return rs.getLong(field.getName());
        }
        if(Float.class == type || float.class == type){
            return rs.getFloat(field.getName());
        }
        if(Double.class == type || double.class == type){
            return rs.getDouble(field.getName());
        }
        if(String.class == type){
            return rs.getString(field.getName());
        }

        return rs.getString(field.getName());
    }

    private String upperCapital(String name) {
        String first = name.substring(0, 1);
        String tail = name.substring(1);
        return first.toUpperCase() + tail;
    }

    /**
     * 获取方法参数的泛型
     * @param method 方法名称
     * @return
     */
    public Type[] getClassType(Method method){
        //获取到方法的参数列表
        Type[] parameterTypes = method.getGenericParameterTypes();
        for (Type type : parameterTypes) {
            //只有带泛型的参数才是这种Type，所以得判断一下
            if(type instanceof ParameterizedType){
                ParameterizedType parameterizedType = (ParameterizedType) type;
                //获取参数的类型
                //System.out.println(parameterizedType.getRawType());
                //获取参数的泛型列表
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                for (Type type2 : actualTypeArguments) {
                    //System.out.println(type2);
                }
                return actualTypeArguments;
            }
        }
        return null;
    }

    /**
     *  获取方法返回参数的泛型
     * @param method 方法
     * @return
     */
    public Type[] getClassReturnValType(Method method){
        //获取返回值的类型，此处不是数组，请注意智商，返回值只能是一个
        Type genericReturnType = method.getGenericReturnType();
        //获取返回值的泛型参数
        if(genericReturnType instanceof ParameterizedType){
            Type[] actualTypeArguments = ((ParameterizedType)genericReturnType).getActualTypeArguments();
            /*for (Type type : actualTypeArguments) {
                System.out.println(type);
            }*/
            return actualTypeArguments;
        }
        return null;
    }
}
