package com.lcydream.project.clone;

import java.io.IOException;


public class CloneDemo {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Teacher teacher=new Teacher();
        teacher.setName("magic");

        Student student=new Student();
        student.setName("kitty");
        student.setAge(35);
        student.setTeacher(teacher);
        //克隆一个对象
        Student student2=(Student) student.deepClone();


        student2.getTeacher().setName("luo");
        System.out.println(student);
        System.out.println(student2);


    }
}
