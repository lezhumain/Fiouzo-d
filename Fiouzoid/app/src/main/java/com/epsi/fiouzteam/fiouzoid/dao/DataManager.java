package com.epsi.fiouzteam.fiouzoid.dao;

import com.epsi.fiouzteam.fiouzoid.dao.group.GroupDao;
import com.epsi.fiouzteam.fiouzoid.dao.group.IGroupDao;
import com.epsi.fiouzteam.fiouzoid.dao.joints.GroupRessources;
import com.epsi.fiouzteam.fiouzoid.dao.joints.GroupUsers;
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
    public static void Manage()
    {
        UserDao userHelper = Database.mUserDao;
        GroupDao groupHelper = Database.mGroupDao;

        DeleteUsers(userHelper);
        SaveUsers(userHelper);

        DeleteGroups(groupHelper);
        SaveGroups(groupHelper);

        DeleteGroupUsers(groupHelper);
        SaveGroupUsers(groupHelper);

        DeleteGroupRessources(groupHelper);
        SaveGroupRessources(groupHelper);
    }

    private static void DeleteGroupRessources(GroupDao groupHelper) {
        groupHelper.deleteAllGroupRessources();
    }

    private static void DeleteGroupUsers(GroupDao groupHelper) {
        groupHelper.deleteAllGroupUsers();
    }

    private static void DeleteGroups(GroupDao helper)
    {
        helper.deleteAllGroups();
    }

    private static void DeleteUsers(UserDao helper)
    {
        helper.deleteUsers();
    }

    private static void SaveUsers(UserDao helper)
    {
        List<User> users = UserService.getAllUsers();
        helper.addUsers(users);
    }

    private static void SaveGroups(GroupDao helper)
    {
        List<Group> groups = GroupService.getAllGroups();
        helper.addGroups(groups);
    }

    private static void SaveGroupRessources(GroupDao groupHelper) {
        List<GroupRessources> groups = GroupService.getGroupRessources();
        groupHelper.addGroupRessources(groups);
    }

    private static void SaveGroupUsers(GroupDao groupHelper) {
        List<GroupUsers> groups = GroupService.getGroupUsers();
        groupHelper.addGroupUsers(groups);
    }
}
