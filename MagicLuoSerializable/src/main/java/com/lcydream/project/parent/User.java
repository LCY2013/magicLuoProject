package com.lcydream.project.parent;


import java.io.Serializable;

public class User extends SuperUser implements Serializable {

    //private static final long serialVersionUID = 6244837929799767391L;

    @Override
    public String toString() {
        return "User{} " + super.toString();
    }
}
