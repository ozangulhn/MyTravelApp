package com.example.hezeyan.myapplication;

public class User {
    public String id, name, surname, email, phone, picture;
    public User(){

    }

    public User(String id, String name, String surname, String email, String phone, String picture) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.picture = picture;
    }
}
