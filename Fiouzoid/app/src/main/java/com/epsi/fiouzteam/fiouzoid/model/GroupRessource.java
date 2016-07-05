package com.epsi.fiouzteam.fiouzoid.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Dju on 02/07/2016.
 */
public class GroupRessource extends Entity {
    private static final String TAG = "GroupRessource";
    private String repo;
    private String resource;
    private int quantity;
    private int idRessource;

    @Override
    public String toJson() {
        return null;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static List<GroupRessource> FromJson(String json)
    {
        Gson gson = new GsonBuilder().create();
        Type listType = new TypeToken<List<GroupRessource>>(){}.getType();

        Log.d(TAG, json);

        List<GroupRessource> groups = gson.fromJson(json, listType);

        return groups;
    }

    @Override
    public String toString() {
        return "GroupRessource{" +
                "repo='" + repo + '\'' +
                ", resource='" + resource + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    public int getIdRessource() {
        return id;
    }

    public void setIdRessource(int idRessource) {
        this.id = idRessource;
    }

    /*
    @Override
    public String toString() {
        return toJson();
    }
    */
}
