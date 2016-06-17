package com.epsi.fiouzteam.fiouzoid.dao.user;

import com.epsi.fiouzteam.fiouzoid.model.User;

import java.util.*;

public interface IUserDao
{
   User fetchById(int userId);
   List<User> fetchAllUsers();
   List<User> fetchAllByGroup(int groupId);
   // add user
   boolean addUser(User user);
   // add users in bulk
   boolean addUsers(List<User> users);
   boolean deleteAllUsers();
}