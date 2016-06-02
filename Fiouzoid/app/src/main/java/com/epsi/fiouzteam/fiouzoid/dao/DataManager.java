package com.epsi.fiouzteam.fiouzoid.dao;

import com.epsi.fiouzteam.fiouzoid.dao.group.GroupDao;
import com.epsi.fiouzteam.fiouzoid.dao.user.UserDao;
import com.epsi.fiouzteam.fiouzoid.model.Group;
import com.epsi.fiouzteam.fiouzoid.model.User;
import com.epsi.fiouzteam.fiouzoid.service.GroupService;
import com.epsi.fiouzteam.fiouzoid.service.UserService;

import java.util.List;

/**
 * Created by Dju on 02/06/2016.
 */
public class DataManager
{
    public static void SaveUsers()
    {
        UserDao helper = Database.mUserDao;
        List<User> users = UserService.getAllUsers();

        helper.addUsers(users);
    }

    public static void SaveGroups()
    {
        GroupDao helper = Database.mGroupDao;
        List<Group> groups = GroupService.getAllGroups();

        helper.addGroups(groups);
    }
}
