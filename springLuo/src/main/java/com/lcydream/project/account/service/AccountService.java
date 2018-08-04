package com.lcydream.project.account.service;

import com.lcydream.project.account.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * AccountService
 *
 * @author Luo Chun Yun
 * @date 2018/7/29 15:39
 */
@Service
public class AccountService {

    @Autowired
    private AccountDao  accountDao;
    @Autowired
    private AnnotationAccountService annotationAccountService;

    /**
     *  转出
     * @param out 转出账号
     * @param money 转出金额
     * @throws Exception 异常
     *//*
    public int transferOut(final String out,final Double money)throws Exception{
        if(money==0){
            throw new Exception("自定义转出异常");
        }
        return accountDao.updateForOut(out, money);
    }

    *//**
     *  转入
     * @param in 转入账户
     * @param money 转入金额
     * @return 返回int
     * @throws Exception 异常
     *//*
    public int transferIn(final String in,final Double money) throws Exception{
        if(money==null){
            throw new Exception("自定义转入异常");
        }
        return accountDao.updateForIn(in,money);
    }*/

    /**
     * 转账逻辑
     * @param out 转出账户
     * @param in 转入账户
     * @param money 转账金额
     */
    public void transfer(final String out,final String in,final Double money) throws Exception{

        if(out == null || in == null ){
            throw new Exception("转账用户不能为空");
        }

        if(out.equals(in)){
            throw new Exception("不允许自己转账给自己");
        }

        //查询转出账户余额信息
        Double moneyOut = accountDao.selectAccount(out);

        if(moneyOut == null){
            throw new Exception("账户不存在");
        }

        //判断转出账户余额是否充足
        if(moneyOut.compareTo(money) < 0){
            throw new Exception("余额不足");
        }


        System.out.println(annotationAccountService.transferAnnotationIn(in,money));

        System.out.println(annotationAccountService.transferAnnotationOut(out,money));



        //int updateForOut = transferOut(out,money);

        //int updateForIn = transferIn(in, null);

        /*if(updateForIn == 0 || updateForOut == 0){
            throw new Exception("转账失败");
        }*/
    }


}
