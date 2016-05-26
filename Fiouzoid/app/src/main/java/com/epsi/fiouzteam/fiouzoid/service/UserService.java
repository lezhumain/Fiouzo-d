package com.epsi.fiouzteam.fiouzoid.service;

import com.epsi.fiouzteam.fiouzoid.http.HttpHelper;
import com.epsi.fiouzteam.fiouzoid.model.User;

/**
 * Created by Dju on 21/05/2016.
 */
public class UserService {
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

    public static String getUserByIdTest(int id)
    {
        String url = "http://jsonplaceholder.typicode.com/posts/" + id;
        HttpHelper helper = new HttpHelper(url, null);
        String resp = helper.Get();

        return resp;
    }

}
