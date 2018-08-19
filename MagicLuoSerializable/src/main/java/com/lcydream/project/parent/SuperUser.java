package com.lcydream.project.parent;

import java.io.Serializable;


public class SuperUser implements Serializable{

    //private static final long serialVersionUID = 6244837929799767391L;
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "SuperUser{" +
                "age=" + age +
                '}';
    }
}
