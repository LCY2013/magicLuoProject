package com.lcydream.project.framework.resolver;

import com.lcydream.project.framework.model.ModelAndView;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ViewResolver
 * 视图解析器
 *
 * @author Luo Chun Yun
 * @date 2018/7/22 10:54
 */
public class ViewResolver {

    /**
     * 视图名称
     */
    private String viewName;

    /**
     * 视图文件
     */
    private File viewFile;

    public ViewResolver(String viewName, File viewFile) {
        this.viewName = viewName;
        this.viewFile = viewFile;
    }

    public String getViewName() {
        return viewName;
    }

    /**
     * 解析文件
     *
     * @param mv
     * @return
     */
    public String parse(ModelAndView mv) {
        RandomAccessFile randomAccessFile = null;
        try {
            StringBuffer sb = new StringBuffer();
            //开启模板的只读模式
            randomAccessFile = new RandomAccessFile(this.viewFile, "r");
            String line = null;
            while (null != (line = randomAccessFile.readLine())) {
                //利用正则去匹配模板中的变量
                Matcher matcher = matcher(line);
                while (matcher.find()) {
                    //匹配一整行的模板参数
                    for (int i = 1; i <= matcher.groupCount(); i++) {
                        //获取正则中的参数名称
                        String paramName = matcher.group(i);
                        //获取这个模板的所有赋值变量
                        Object paramValue = mv.getModel().get(paramName);
                        if (paramValue == null) {
                            continue;
                        }
                        //将符合条件的变量赋值
                        line = line.replaceAll("#\\{" + paramName + "\\}", paramValue + "");
                    }
                }
                //构建一整行的模板数据
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
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
}
