package com.epsi.fiouzteam.fiouzoid.service;

import android.util.Log;

import com.epsi.fiouzteam.fiouzoid.dao.joints.GroupRessources;
import com.epsi.fiouzteam.fiouzoid.dao.joints.GroupUsers;
import com.epsi.fiouzteam.fiouzoid.http.HttpHelper;
import com.epsi.fiouzteam.fiouzoid.model.Group;
import com.epsi.fiouzteam.fiouzoid.model.User;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

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

    public static List<Group> getAllGroups() {
        List<Group> u = new ArrayList<>();
        final int nbTestGroups = 5;

        for(int i = 1; i < nbTestGroups; ++i)
            u.add(getTestGroupById(i));

        return u;
    }

    public static List<GroupRessources> getGroupRessources() {
        List<GroupRessources> grs = new ArrayList<>();
        final int max = 3;

        for(int i = 0; i < max; ++i)
        { // TODO finish
            GroupRessources gr = new GroupRessources();
            gr.setIdGroup( (i+1>3?i+1:3) );
            gr.setIdTypeRessource( 5 );
            gr.setQuantite( 5 );

            grs.add(gr);
        }

        return grs;
    }

    public static List<GroupUsers> getGroupUsers() {
        List<GroupUsers> grs = new ArrayList<>();
        final int max = 3;

        for(int i = 0; i < max; ++i) { // TODO finish
            GroupUsers gu = new GroupUsers();
            gu.setIdGroup(1);
            gu.setIdGroup(3);

            grs.add(gu);
        }

        return grs;
    }

    public static Hashtable<String, Integer> getStock() {
        // TODO: get stock from service
        //final int max = 3;
        final String[] categories = new String[]{"Fiouz", "Binouz", "Pepouz"};
        Hashtable<String, Integer> stocks = new Hashtable<>();

        for(int i = 0; i < categories.length; ++i)
        {
            Random r = new Random();
            int rValue = r.nextInt(20);

            stocks.put(categories[i], rValue);
        }

        return stocks;
    }
}
