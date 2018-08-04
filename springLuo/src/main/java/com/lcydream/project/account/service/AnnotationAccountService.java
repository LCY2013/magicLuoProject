package com.lcydream.project.account.service;

import com.lcydream.project.account.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * AnnotationAccountService
 *
 * @author Luo Chun Yun
 * @date 2018/7/29 17:09
 */
@Service
public class AnnotationAccountService {

    @Autowired
    private AccountDao accountDao;

    /**
     *  转出
     * @param out 转出账号
     * @param money 转出金额
     * @throws Exception 异常
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public int transferAnnotationOut(final String out,final Double money)throws Exception{
        if(money==0){
            throw new Exception("自定义转出异常");
        }
        return accountDao.updateForOut(out, money);
    }

    /**
     *  转入
     * @param in 转入账户
     * @param money 转入金额
     * @return 返回int
     * @throws Exception 异常
     */
    @Transactional(propagation = Propagation.NESTED,rollbackFor = Exception.class)
    public int transferAnnotationIn(final String in,final Double money) throws Exception{
        if(money==null){
            throw new Exception("自定义转入异常");
        }
        return accountDao.updateForIn(in,money);
    }

}
