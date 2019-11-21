package com.example.calendarapp;

public class Teacher {
    public int id;
    public String name;
    public String userName;
    public String password;
    public String email;

    public Teacher(){
    }

    public Teacher(int id, String userName, String password, String email) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    @Override
    public String toString() {
        return ("\n" + "Teacher_Name:"+this.getName()+
                " ID: "+ this.getId() +
                " Email: "+ this.getEmail());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
