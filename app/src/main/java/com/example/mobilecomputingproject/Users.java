package com.example.mobilecomputingproject;

public class Users {
    String name,email,pass,date,phone,location;

    public Users(){}

    public Users(String name, String email, String pass, String date, String phone, String location) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.date = date;
        this.phone = phone;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
