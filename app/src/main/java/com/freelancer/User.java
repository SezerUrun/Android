package com.freelancer;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("Id")
    private int id;
    @SerializedName("Name")
    private String name;
    @SerializedName("Mail")
    private String mail;
    @SerializedName("Password")
    private String password;
    @SerializedName("Credit")
    private int credit;

    public User(int id, String name, String mail, String password, int credit) {
        this.id=id;
        this.name = name;
        this.mail=mail;
        this.password=password;
        this.credit=credit;
    }

    public User(String mail, String password){
        this.mail=mail;
        this.password=password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCredit() { return credit; }

    public void setCredit(int credit) { this.credit = credit; }
}
