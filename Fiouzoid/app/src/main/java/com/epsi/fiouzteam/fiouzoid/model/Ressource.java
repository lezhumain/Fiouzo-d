package com.epsi.fiouzteam.fiouzoid.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by ThaZalman on 06/07/2016.
 */
public class Ressource extends Entity {
    private String name;
    private String unit;
    private int price;
    private int idRepo;

    @Override
    public String toJson() {
        Gson gson = new GsonBuilder().create();
        String str = gson.toJson(this, this.getClass());

        return str;
    }

    public static List<Ressource> FromJson(String json) {
        Gson gson = new GsonBuilder().create();
        Type listType = new TypeToken<List<Ressource>>(){}.getType();
        List<Ressource> ressources = gson.fromJson(json, listType);

        return ressources;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getIdRepo() {
        return idRepo;
    }

    public void setIdRepo(int idRepo) {
        this.idRepo = idRepo;
    }
}
