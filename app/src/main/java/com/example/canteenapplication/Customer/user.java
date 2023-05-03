package com.example.canteenapplication.Customer;

public class user {
    String id, name, phone, password;

    public user(String id, String name, String phone, String password) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.password = password;
    }

    public user() {
    }

    public String getId() {
            return id;
    }

    public void setId(String id) {
            this.id = id;
    }

    public String getName() {
            return name;
    }

    public void setName(String name) {
            this.name = name;
    }

    public String getPhone() {
            return phone;
    }

    public void setPhone(String phone) {
            this.phone = phone;
    }

    public String getPassword() {
            return password;
    }

    public void setPassword(String password) {
            this.password = password;
    }
}
