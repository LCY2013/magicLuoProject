package com.lcydream.project.designmode.proxy.custom;

/**
 * MagicLuo 被代理人
 *
 * @author Luo Chun Yun
 * @date 2018/6/18 21:32
 */
public class MagicLuo implements Person {

    private String sex="男";
    private String name="magicLuo";

    @Override
    public void findLove() {
        System.out.println("我叫"+this.name+",性别:"+this.sex+",我的要求是:");
        System.out.println("女");
        System.out.println("170");
        System.out.println("美女");
    }

    @Override
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
