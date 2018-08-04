package com.lcydream.project.springsource.account;

import com.lcydream.project.account.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * AccountServiceTest
 *
 * @author Luo Chun Yun
 * @date 2018/7/29 15:59
 */
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountServiceTest {

    @Autowired
    AccountService accountService;

    /**
     * 测试转账
     */
    @Test
    public void testTransfer(){
        try {
            for (int i = 0;i<1;i++) {
                accountService.transfer("lcy", "yyr", 1000D);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
