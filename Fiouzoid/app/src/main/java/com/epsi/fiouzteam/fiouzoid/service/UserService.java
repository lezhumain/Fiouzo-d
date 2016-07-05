package com.epsi.fiouzteam.fiouzoid.service;

import android.util.Log;

import com.epsi.fiouzteam.fiouzoid.http.HttpHelper;
import com.epsi.fiouzteam.fiouzoid.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dju on 21/05/2016.
 */
public class UserService {
    private static final String BASE_URL = "http://davanture.fr:8000/";
    private static final String TAG = "UserService";

    public static List<User> getAllUsers()
    {
        List<User> u = new ArrayList<>();
        final int nbTestUser = 10;


        for(int i = 1; i < nbTestUser; ++i)
            u.add(getTestUserById(i));

        Log.d(TAG, "getAllUsers done");
        return u;
    }

    public static User getUserById(int id)
    {
        String url = BASE_URL + "getUserById/" + id;
        HttpHelper helper = new HttpHelper(url, null);
        String resp = helper.Get();
        resp = resp.substring(1, resp.length() - 2);

        Log.i(TAG, resp);
        User user = new User();
        return User.fromJson(resp);
    }

    public static User getTestUserById(int id)
    {
        String nickName = "nomprénom" + id,
                json = "{\n" +
                        "    \"$id\": \"1\",\n" +
                        "    \"id\": " + String.valueOf(id) + ",\n" +
                        "    \"username\": \"lucdef" + String.valueOf(id) + "\",\n" +
                        "    \"firstName\": \"lucas" + String.valueOf(id) + " \",\n" +
                        "    \"lastName\": \"defrance" + String.valueOf(id) + "\",\n" +
                        "    \"isAdmin\": false\n" +
                        "  }";

        //User user = new User();
        return User.fromJson(json);
    }

    // unused
    public static User getUserByIdTest(int id)
    {
        //String url = "http://jsonplaceholder.typicode.com/posts/" + id;
        String url = BASE_URL + "getUserById/" + id;
        HttpHelper helper = new HttpHelper(url, null);
        String resp = helper.Get();
        resp = resp.substring(1, resp.length() - 2);

        Log.i(TAG, resp);
        User user = new User();
        return User.fromJson(resp);
    }

    public static List<User> getUsersByGroup(int idGroup)
    {
        //String url = "http://jsonplaceholder.typicode.com/posts/" + id;
        String url = "http://api.davanture.fr/api/repo/getUsersRepo?idRepo=" + String.valueOf(idGroup);
        HttpHelper helper = new HttpHelper(url, null);
        String resp = helper.Get();

        //Log.i(TAG, "json url:\n\t" + url);
        Log.i(TAG, "json response:\n\t" + resp);

        List<User> lg = User.FromJson(resp);
        Log.i(TAG, "user toString:\n\t" + lg.get(0).toString());
        return lg;
    }
}
