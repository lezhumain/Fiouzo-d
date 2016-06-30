package com.epsi.fiouzteam.fiouzoid.service;

import android.util.Log;

import com.epsi.fiouzteam.fiouzoid.http.HttpHelper;
import com.epsi.fiouzteam.fiouzoid.model.Group;
import com.epsi.fiouzteam.fiouzoid.model.User;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dju on 21/05/2016.
 */
public class GroupService {
    private static final String TAG = "GroupService";

    public static Group getGroupById(int id)
    {
        String name = "nomGroupe" + id,
                description = "description" + id;

        return new Group(id, name, description);
    }

    public static Group getTestGroupById(int id)
    {
        String name = "groupName" + id,
                description = "super description " + id,
                jsonStr = "{'name': '" +
                        name + "', " +
                        "'description': '" +
                        description + "', " +
                        "'id': " + id + ", ";

        List<User> users = null;
        if(id == 0) {
            users = UserService.getAllUsers();
        }
        jsonStr = fillWithUsers(users, jsonStr) + '}';


        Log.i(TAG, "jsonStr: " + jsonStr);

        Group group = new Group();
        return group.fromJson(jsonStr);
//        return (new GsonBuilder().create()).fromJson(jsonStr, Group.class);
    }

    private static String fillWithUsers(List<User> users, String jsonStr) {
        jsonStr += "'users': [";

        if (users != null)
        {
            for (User u : users) {
                jsonStr += u.toJson() + ',';
            }
            jsonStr = jsonStr.substring(0,jsonStr.length() - 1);
        }

        return  jsonStr + ']';
    }

    public static String getGroupByIdTest(int id)
    {
        String url = "http://jsonplaceholder.typicode.com/posts/" + id;
        HttpHelper helper = new HttpHelper(url, null);
        String resp = helper.Get();

        return resp;
    }

//    public static List<Group> getAllGroups(int idUser) {
//        List<Group> u = new ArrayList<>();
//        final int nbTestGroups = 5;
//
//        for(int i = 1; i < nbTestGroups; ++i)
//            u.add(getTestGroupById(i));
//
//        return u;
//    }

    public static List<Group> getAllGroups(int idUser) {
        String url = "http://api.davanture.fr/api/repo/getallstock?idUser=" + String.valueOf(idUser);
        HttpHelper helper = new HttpHelper(url, null);
        String resp = helper.Get();

        Log.i(TAG, "json response:\n\t" + resp);
        return Group.FromJson(resp);
    }
}
