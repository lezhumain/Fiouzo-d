package com.epsi.fiouzteam.fiouzoid.service;

import com.epsi.fiouzteam.fiouzoid.model.User;

/**
 * Created by Dju on 21/05/2016.
 */
public class UserService {
    public static User getUserById(int id)
    {



        String firstName = "prénom" + id,
                lastName = "nom" + id,
                nickName = "nomprénom" + id;

        return new User(id, firstName, lastName, nickName);
    }
}
