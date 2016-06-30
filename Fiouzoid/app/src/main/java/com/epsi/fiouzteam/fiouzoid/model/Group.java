package com.epsi.fiouzteam.fiouzoid.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Group extends Entity
{


    private String name;
    private String description;
    private List<User> users;
    private Hashtable<String, Integer> stock = new Hashtable<>();


    public Group() {
        super();
        // test
        String[] categories = new String[]{"Fiouz", "Binouz", "Pepouz"};
        for(int i = 0; i < categories.length; ++i)
        {
            Random r = new Random();
            int rValue = r.nextInt(20);
            stock.put(categories[i], rValue);
        }
        // end test

        //stock = new Hashtable<>();
        users = new ArrayList<>();
    }

    public Group(int id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;

        // test
        String[] categories = new String[]{"Fiouz", "Binouz", "Pepouz"};
        for(int i = 0; i < categories.length; ++i)
        {
            Random r = new Random();
            int rValue = r.nextInt();
            stock.put(categories[i], rValue);
        }
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

    public static List<Group> FromJson(String json)
    {
        Gson gson = new GsonBuilder().create();
        Type listType = new TypeToken<List<Group>>(){}.getType();
        List<Group> groups = (List<Group>) gson.fromJson(json, listType);

//        this.id = group.id;
//        this.name = group.name;
//        this.description = group.description;
//
//        if(this.users == null)
//            this.users = new ArrayList<>();
//        for (User u :
//                group.users) {
//            this.users.add(u);
//        }

        return groups;
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
        return toJson();
    }

    public Hashtable<String, Integer> getStock() {
        return stock;
    }

    public void setStock(Hashtable<String, Integer> stock) {
        this.stock = stock;
    }

//    @Override
//    public String toString() {
//        String str = "Group{\n" +
//                "name='" + name + "\'\n" +
//                ", description='" + description + "\'\n" +
//                ", users=\n";
//
//        for (User u :
//                users) {
//            str += '\t' + u.toString() + ",\n";
//        }
//
//        str += "stock=\n";
//
//        Object[] stockKeys = stock.keySet().toArray();
//        for (int i = 0; i < stockKeys.length; ++i) {
//            str += '\t' + stockKeys[i].toString() + ": " + stock.get(stockKeys[i]) + ",\n";
//        }
//
//        str += '}';
//
//        return str;
//    }
}