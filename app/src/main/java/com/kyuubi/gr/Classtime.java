package com.kyuubi.gr;

/**
 * Created by Administrator on 17/05/2017.
 */
import java.io.Serializable;

public class Classtime implements Serializable{
    String time;
    String classname;

    public Classtime(String classname, String time) {
        this.time = time;
        this.classname = classname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    @Override
    public String toString() {
        return this.classname +" ("+ this.time+")";
    }
}
