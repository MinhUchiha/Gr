package com.kyuubi.gr;

import java.io.Serializable;

/**
 * Created by Administrator on 22/05/2017.
 */

public class User implements Serializable {
    String name;
    String username;

    public User(String name, String username) {
        this.name = name;
        this.username = username;
    }


    @Override
    public String toString() {
        return this.name +" ("+ this.username+")";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
