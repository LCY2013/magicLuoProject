package com.lcydream.project;

import com.alibaba.druid.filter.config.ConfigTools;
import org.junit.Test;

/**
 * TestMain
 *
 * @author Luo Chun Yun
 * @date 2018/11/19 15:02
 */
public class TestMain {

    /**
     * 获取druid的编码密码
     */
    @Test
    public void druidPassword(){
        try {
            ConfigTools.main(new String[]{"123456"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
