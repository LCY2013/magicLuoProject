package com.lcydream.project.handler;

import com.lcydream.project.config.MagicConfiguration;
import com.lcydream.project.config.MapperRegistory;
import com.lcydream.project.result.ResultSetHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * StatementHandler
 *
 * @author Luo Chun Yun
 * @date 2018/8/5 21:29
 */
public class StatementHandler {
    private static final Logger logger = LoggerFactory.getLogger(StatementHandler.class);
    private MagicConfiguration magicConfiguration;
    private ResultSetHandler resultSetHandler;

    public StatementHandler(MagicConfiguration magicConfiguration) {
        this.magicConfiguration = magicConfiguration;
        this.resultSetHandler = new ResultSetHandler();
    }

    public <T> T handler(String nameSpace, Object parameter){
        Connection connection = null;
        try {
            MapperRegistory.MapperData mapperData = magicConfiguration.getMapperRegistory().get(nameSpace);
            if (mapperData != null) {
                String sql = mapperData.getSql();
                if (StringUtils.isBlank(sql)) {
                    logger.error("不存在sql的操作，不合法");
                    return null;
                }
                connection = getConnection();
                connection.setAutoCommit(false);
                if (sql.trim().toUpperCase().startsWith(SqlCommandType.INSERT.name())) {
                    String readSql = createRealSql(mapperData.getSql(), (Object[]) parameter, mapperData);
                    logger.info("新增sql为："+sql);
                    PreparedStatement pstmt;
                    if (parameter != null) {
                        pstmt = connection.prepareStatement(
                                readSql);
                    } else {
                        pstmt = connection.prepareStatement(
                                readSql);
                    }
                    int i = pstmt.executeUpdate();
                    if (mapperData.getMethod().getReturnType() == Integer.class || mapperData.getMethod().getReturnType() == int.class) {

                        return (T) new Integer(i);
                    } else if (mapperData.getMethod().getReturnType() == Long.class || mapperData.getMethod().getReturnType() == long.class) {
                        return (T) new Integer(i);
                    } else if (mapperData.getMethod().getReturnType() == Void.class || mapperData.getMethod().getReturnType() == void.class) {
                        return null;
                    } else {
                        logger.error("插入的返回值类型不支持");
                        connection.rollback();
                        throw new RuntimeException("插入的返回值类型不支持");
                    }
                } else if (sql.trim().toUpperCase().startsWith(SqlCommandType.UPDATE.name())) {
                    String readSql = createRealSql(mapperData.getSql(), (Object[]) parameter, mapperData);
                    logger.info("更新sql为："+sql);
                    PreparedStatement pstmt;
                    if (parameter != null) {
                        pstmt = connection.prepareStatement(
                                readSql);
                    } else {
                        pstmt = connection.prepareStatement(
                                readSql);
                    }
                    int i = pstmt.executeUpdate();
                    /*if(i==1){
                        throw new RuntimeException("123");
                    }*/
                    if (mapperData.getMethod().getReturnType() == Integer.class || mapperData.getMethod().getReturnType() == int.class) {
                        return (T) new Integer(i);
                    } else if (mapperData.getMethod().getReturnType() == Long.class || mapperData.getMethod().getReturnType() == long.class) {
                        return (T) new Integer(i);
                    } else if (mapperData.getMethod().getReturnType() == Void.class || mapperData.getMethod().getReturnType() == void.class) {
                        return null;
                    } else {
                        logger.error("更新的返回值类型不支持");
                        connection.rollback();
                        throw new RuntimeException("更新的返回值类型不支持");
                    }
                } else if (sql.trim().toUpperCase().startsWith(SqlCommandType.DELETE.name())) {
                    String readSql = createRealSql(mapperData.getSql(), (Object[]) parameter, mapperData);
                    logger.info("删除sql为："+sql);
                    PreparedStatement pstmt;
                    if (parameter != null) {
                        pstmt = connection.prepareStatement(
                                readSql);
                    } else {
                        pstmt = connection.prepareStatement(
                                readSql);
                    }
                    int i = pstmt.executeUpdate();
                    if (mapperData.getMethod().getReturnType() == Integer.class || mapperData.getMethod().getReturnType() == int.class) {
                        return (T) new Integer(i);
                    } else if (mapperData.getMethod().getReturnType() == Long.class || mapperData.getMethod().getReturnType() == long.class) {
                        return (T) new Integer(i);
                    } else if (mapperData.getMethod().getReturnType() == Void.class || mapperData.getMethod().getReturnType() == void.class) {
                        return null;
                    } else {
                        logger.error("删除的返回值类型不支持");
                        connection.rollback();
                        throw new RuntimeException("删除的返回值类型不支持");
                    }
                } else if (sql.trim().toUpperCase().startsWith(SqlCommandType.SELECT.name())) {
                    String readSql = createRealSql(mapperData.getSql(), (Object[]) parameter, mapperData);
                    logger.info("查询sql为："+sql);
                    PreparedStatement pstmt = null;
                    if (parameter != null) {
                        pstmt = connection.prepareStatement(readSql);
                    } else {
                        pstmt = connection.prepareStatement(readSql);
                    }
                    pstmt.execute();
                    return resultSetHandler.handle(pstmt, mapperData);
                } else if (sql.trim().toUpperCase().startsWith(SqlCommandType.FLUSH.name())) {
                    connection.commit();
                }
            }
            return null;
        } catch (Exception e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (Exception ex) {
            }
            logger.error("数据库错误：" + e.getLocalizedMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.commit();
                    connection.close();
                }
            } catch (Exception e) {
                logger.error("数据库错误：" + e.getLocalizedMessage());
            }
        }
        return null;
    }

    /**
     * 正则预编译提高加载速度,匹配#{}这里面有至少一个任意的字符
     */
    private static Pattern pattern = Pattern.compile("#\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);

    /**
     * 正则匹配
     *
     * @param matStr
     * @return
     */
    private Matcher matcher(String matStr) {
        Matcher matcher = pattern.matcher(matStr);
        return matcher;
    }

    private String createRealSql(String sql, Object[] args, MapperRegistory.MapperData mapperData) throws Exception {
        //利用正则去匹配模板中的变量
        Matcher matcher = matcher(sql);
        int i = 1;
        while (matcher.find()) {
            Class<?>[] methodParamTypes = mapperData.getMethod().getParameterTypes();
            //匹配一整行的模板参数
            //获取正则中的参数名称
            String paramName = matcher.group(1);
            //获取这个模板的所有赋值变量
            if (methodParamTypes[i-1] == String.class) {
                sql = sql.replaceAll("#\\{" + paramName + "\\}",
                        "'" + args[i-1] + "'");
            } else {
                //将符合条件的变量赋值
                sql = sql.replaceAll("#\\{" + paramName + "\\}",
                        args[i-1] + "");
            }
            i++;
        }
        return sql;
    }

    public Connection getConnection() throws SQLException {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/magic?useUnicode=true&characterEncoding=utf-8&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String username = "root";
        String password = "123456";
        Connection conn = null;
        try {
            //classLoader,加载对应驱动
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public enum SqlCommandType {
        UNKNOWN, INSERT, UPDATE, DELETE, SELECT, FLUSH;
    }
}
