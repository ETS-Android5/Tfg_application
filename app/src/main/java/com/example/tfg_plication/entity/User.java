package com.example.tfg_plication.entity;

import android.widget.EditText;
import android.widget.TextView;

public class User {
    private long id;
    private String name;
    private String pass;

    public User(){}
    public User(long id, String name, String pass) {
        this.id = id;
        this.name = name;
        this.pass = pass;
    }

    public User(TextView boxUser, EditText pass) {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }


}
