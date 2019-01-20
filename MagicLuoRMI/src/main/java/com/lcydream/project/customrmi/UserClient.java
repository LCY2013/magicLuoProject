package com.lcydream.project.customrmi;

import java.io.IOException;


public class UserClient {

    public static void main(String[] args) throws IOException {
        User user=new User_Stub();

        int age=user.getAge();

        System.out.println(age);
    }
}
