package com.epsi.fiouzteam.fiouzoid.service;

import android.util.Log;

import com.epsi.fiouzteam.fiouzoid.http.HttpHelper;
import com.epsi.fiouzteam.fiouzoid.model.Group;
import com.epsi.fiouzteam.fiouzoid.model.GroupRessource;
import com.epsi.fiouzteam.fiouzoid.model.User;

import java.util.Hashtable;
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
        idUser = 1;
        String url = "http://api.davanture.fr/api/repo/getByUser?idUser=" + String.valueOf(idUser);
        HttpHelper helper = new HttpHelper(url, null);
        String resp = helper.Get();

        //Log.i(TAG, "json url:\n\t" + url);
        //Log.i(TAG, "json response:\n\t" + resp);

        // TODO remove
        /*
        resp = "[\n" +
                "  {\n" +
                "    \"$id\": \"1\",\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"groupeBdd1\",\n" +
                "    \"description\": \"un groupe de la bdd maggle\"\n" +
                "  }\n" +
                "]";
        */
        //


        List<Group> lg = Group.FromJson(resp);
        Log.i(TAG, "group toString:\n\t" + lg.get(0).toString());
        return lg;
    }

    public static Hashtable<String, Integer> getStocksForGroup(int idGroup) {
    //public static void getStocksForGroup(int idGroup) {
        Hashtable<String, Integer> values = new Hashtable<>();
        String url = "http://api.davanture.fr/api/repo/getallstock?idUser=" + String.valueOf(idGroup);
        HttpHelper helper = new HttpHelper(url, null);
        String resp = helper.Get();

        List<GroupRessource> lst = GroupRessource.FromJson(resp);
        Log.i(TAG, "json response:\n\t" + resp);
        for (GroupRessource gr : lst)
        {
            String key = gr.getResource();
            Log.i(TAG, "GroupRessource:\n\t" + gr.toString());
            if(!values.containsKey(key))
                values.put(key, gr.getQuantity());
        }

        return values;
    }
}
