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
    private static final String BASE_URL = "http://192.168.1.35:8000/";
    private static final String TAG = "UserService";

    public static List<User> getAllUsers()
    {
        List<User> u = new ArrayList<>();
        u.add(getTestUserById(0));
        u.add(getTestUserById(1));

        return u;
    }

    public static User getUserById(int id)
    {
        String nickName = "nomprénom" + id,
                email = "email" + id;

        return new User(id, nickName, email);
    }

    public static User getTestUserById(int id)
    {
        String nickName = "nomprénom" + id,
                email = "email" + id + "@lol.com",
                json = "{'nickName': '" +
                        nickName + "', " +
                        "'email': '" +
                        email + "', " +
                        "'id': " +
                        id + "}";

        User user = new User();
        return user.fromJson(json);
    }

    public static User getUserByIdTest(int id)
    {
        //String url = "http://jsonplaceholder.typicode.com/posts/" + id;
        String url = BASE_URL + "getUserById/" + id;
        HttpHelper helper = new HttpHelper(url, null);
        String resp = helper.Get();
        resp = resp.substring(1, resp.length() - 2);

        Log.i(TAG, resp);
        User user = new User();
        return user.fromJson(resp);
    }

}
