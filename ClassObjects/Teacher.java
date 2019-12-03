package com.example.afterschoolapplication.ClassObjects;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.afterschoolapplication.DB.AppDatabase;

@Entity(tableName = AppDatabase.TEACHER_TABLE)
public class Teacher {

    @PrimaryKey(autoGenerate = true)
    private int teacherID;

    private String name;
    private String password;
    private Boolean loggedIn;
    private Boolean admin;

    public Teacher(String name, String password, Boolean admin){
        this.name = name;
        this.password = password;
        this.admin = admin;
        this.loggedIn = false;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String toString() {
        return "User: " + this.name;
    }



}
