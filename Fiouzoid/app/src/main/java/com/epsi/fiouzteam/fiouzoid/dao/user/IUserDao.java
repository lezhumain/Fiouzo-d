package com.epsi.fiouzteam.fiouzoid.dao.user;

import com.epsi.fiouzteam.fiouzoid.model.User;

import java.util.*;

public interface IUserDao
{
   public User fetchUserById(int userId);
   public List<User> fetchAllUsers();
   // add user
   public boolean addUser(User user);
   // add users in bulk
   public boolean addUsers(List<User> users);
   public boolean deleteAllUsers();
}