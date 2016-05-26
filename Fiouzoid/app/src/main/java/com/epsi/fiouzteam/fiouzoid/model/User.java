package com.epsi.fiouzteam.fiouzoid.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class User extends Entity
{
    private String nickName;
    private String email;


    public User() {
        super();
    }

    public User(int id, String nickName, String email) {
        super(id);
        this.nickName = nickName;
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                '}';

    }

    @Override
    public User fromJson(String json)
    {
        Gson gson = new GsonBuilder().create();
        User user = gson.fromJson(json, User.class);

        this.id = user.id;
        this.nickName = user.nickName;
        this.email = user.email;

        return this;
    }

    @Override
    public String toJson()
    {
        Gson gson = new GsonBuilder().create();
        String str = gson.toJson(this, this.getClass());

        return str;
    }
}