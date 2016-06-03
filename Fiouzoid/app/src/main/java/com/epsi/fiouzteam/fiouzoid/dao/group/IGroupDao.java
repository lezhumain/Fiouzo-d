package com.epsi.fiouzteam.fiouzoid.dao.group;

import com.epsi.fiouzteam.fiouzoid.model.Group;

import java.util.List;

public interface IGroupDao
{
   Group fetchById(int groupId);
   List<Group> fetchAllByUser(int userId);
   List<Group> fetchAllGroups();
   // add group
   boolean addGroup(Group group);
   // add groups in bulk
   boolean addGroups(List<Group> groups);
   boolean deleteAllGroups();
}