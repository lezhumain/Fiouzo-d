package com.epsi.fiouzteam.fiouzoid.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class User extends Entity
{
    // id in Entity base class
    private String username;
    private String firstName;
    private String lastName;
    private boolean isAdmin;
    private List<Group> groups = new ArrayList<>();


    public User()
    {
        super();
    }

    public User(int id, String username, String lastName, String firstName, boolean isAdmin) {
        super(id);
        this.username = username;
        this.lastName = lastName;
        this.firstName = firstName;
        this.isAdmin = isAdmin;
    }


    @Override
    public String toString() {
        return toJson();
    }


    public static User fromJson(String json)
    {
        Gson gson = new GsonBuilder().create();
        User user = gson.fromJson(json, User.class);

        return user;
    }

    @Override
    public String toJson()
    {
        Gson gson = new GsonBuilder().create();

        String str = gson.toJson(this, this.getClass());

        return str;
    }

    public static List<User> FromJson(String json) {
        Gson gson = new GsonBuilder().create();
        Type listType = new TypeToken<List<User>>(){}.getType();
        List<User> Users = gson.fromJson(json, listType);

        return Users;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}