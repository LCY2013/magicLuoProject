package com.lcydream.project;

import java.io.*;


public class SerializeDemo {

    public static void main(String[] args) {
        // 序列化操作
        SerializePerson();

        //反序列化操作
        //Person person=DeSerializePerson();

        //System.out.println(person);
    }

    private static void SerializePerson(){
        try {
            ObjectOutputStream oo=new ObjectOutputStream(new FileOutputStream(new File("person")));
            Person person=new Person();
            person.setAge(18);
            person.setName("magic");
            oo.writeObject(person);
            oo.flush();
            /*ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("person")));
            Person person1=(Person)ois.readObject();*/
            person.setAge(19);
            person.setName("magic1");
            oo.writeObject(person);
            oo.flush();
            System.out.println("序列化成功: "+new File("person").length());
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("person")));
            Person person1=(Person)ois.readObject();
            Person person2=(Person)ois.readObject();
            Person person3 = null;
            if(ois.available()!=0){
                person3=(Person)ois.readObject();
            }
            System.out.println(person1+"->"+person2+"->"+person3);
            oo.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Person DeSerializePerson(){
        ObjectInputStream ois= null;
        try {
            ois = new ObjectInputStream(new FileInputStream(new File("person")));
            Person person=(Person)ois.readObject();
            return person;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
