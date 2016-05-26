package com.epsi.fiouzteam.fiouzoid.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Group extends Entity
{
    private String name;
    private String description;


    public Group() {
        super();
    }

    public Group(int id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;
    }


    @Override
    public Group fromJson(String json)
    {
        Gson gson = new GsonBuilder().create();
        Group group = gson.fromJson(json, Group.class);

        this.id = group.id;
        this.name = group.name;
        this.description = group.description;

        return this;
    }

    @Override
    public String toJson()
    {
        Gson gson = new GsonBuilder().create();
        String str = gson.toJson(this, this.getClass());

        return str;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}