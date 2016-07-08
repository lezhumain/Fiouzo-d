package com.epsi.fiouzteam.fiouzoid.dao;

import com.epsi.fiouzteam.fiouzoid.dao.group.GroupDao;
import com.epsi.fiouzteam.fiouzoid.dao.user.UserDao;
import com.epsi.fiouzteam.fiouzoid.model.Group;
import com.epsi.fiouzteam.fiouzoid.model.GroupRessource;
import com.epsi.fiouzteam.fiouzoid.model.User;
import com.epsi.fiouzteam.fiouzoid.service.GroupService;
import com.epsi.fiouzteam.fiouzoid.service.UserService;
import com.epsi.fiouzteam.fiouzoid.utils.LoggerSql;

import java.util.List;

/**
 * Created by Dju on 02/06/2016.
 */
public class DataManager
{
    private static void SaveUsers(int groupId)
    {
        UserDao helper = Database.mUserDao;
        //List<User> users = UserService.getAllUsers();
        List<User> users = UserService.getUsersByGroup(groupId);

        helper.deleteAllUsers();
        helper.addUsers(users);
    }

    private static void SaveGroups(int userId)
    {
        GroupDao helper = Database.mGroupDao;
        //List<Group> groups = GroupService.getAllGroups(1);
        List<Group> groups = GroupService.getAllGroups(1);

        helper.addGroups(groups);
    }

    private static void SaveGroupUsers(int idGroup) {

        List<User> users = Database.mUserDao.fetchAllUsers();
        Database.mUserDao.addUsersToGroupe(users, idGroup);
    }

    private static void SaveGroupRessource(int idGroup) {
        List<GroupRessource> stocks = GroupService.getStocksForGroup(1);

        Database.mGroupDao.deleteGroupStocks();
        Database.mGroupDao.addStocksToGroup(stocks, idGroup);

        LoggerSql.Log("Groups saved", null, true, null);
    }

    public static void SaveData(int groupId, int appUserId) {
        DataManager.SaveUsers(1);
        DataManager.SaveGroups(appUserId);
        DataManager.SaveGroupUsers(appUserId);
        DataManager.SaveGroupRessource(groupId);
    }
}
