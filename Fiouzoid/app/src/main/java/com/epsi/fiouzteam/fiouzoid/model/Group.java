package com.epsi.fiouzteam.fiouzoid.model;

import com.epsi.fiouzteam.fiouzoid.dao.Database;
import com.epsi.fiouzteam.fiouzoid.service.GroupService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

public class Group extends Entity
{


    private String name;
    private String description;
    private List<User> users;
    private Hashtable<String, Integer> stock = new Hashtable<>();


    public Group() {
        super();
        // test
        /*
        String[] categories = new String[]{"Fiouz", "Binouz", "Pepouz"};
        for(int i = 0; i < categories.length; ++i)
        {
            Random r = new Random();
            int rValue = r.nextInt(20);
            stock.put(categories[i], rValue);
        }
        */
        LoadStock();
        // end test
    }

    public void LoadStock()
    {
        // TODO get it from db AFTER HAVING loaded every stocks
        stock = GroupService.getStock();
    }

    public Group(int id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;

        // test
        /*
        String[] categories = new String[]{"Fiouz", "Binouz", "Pepouz"};
        for(int i = 0; i < categories.length; ++i)
        {
            Random r = new Random();
            int rValue = r.nextInt(20);
            stock.put(categories[i], rValue);
        }
        */
        LoadStock();
        // end test
    }


    @Override
    public Group fromJson(String json)
    {
        Gson gson = new GsonBuilder().create();
        Group group = gson.fromJson(json, Group.class);

        this.id = group.id;
        this.name = group.name;
        this.description = group.description;

        if(this.users == null)
            this.users = new ArrayList<>();
        for (User u :
                group.users) {
            this.users.add(u);
        }

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        /*
        return "Group{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", users=" + users +
                '}';
                */
        return toJson();
    }

    public Hashtable<String, Integer> getStock() {
        if(stock == null || stock.size() == 0)
            LoadStock();

        return stock;
    }

    public void setStock(Hashtable<String, Integer> stock) {
        this.stock = stock;
    }

    public void SetStockAt(String typeRessourceName, int stock) {
        if(!this.stock.containsKey(typeRessourceName))
            return;

        this.stock.remove(typeRessourceName);
        this.stock.put(typeRessourceName, stock);
    }
}