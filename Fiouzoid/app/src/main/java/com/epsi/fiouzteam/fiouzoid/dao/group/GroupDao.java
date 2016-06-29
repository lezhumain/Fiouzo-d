package com.epsi.fiouzteam.fiouzoid.dao.group;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.epsi.fiouzteam.fiouzoid.dao.Database;
import com.epsi.fiouzteam.fiouzoid.dao.DbContentProvider;
import com.epsi.fiouzteam.fiouzoid.dao.joints.GroupRessources;
import com.epsi.fiouzteam.fiouzoid.dao.joints.GroupUsers;
import com.epsi.fiouzteam.fiouzoid.dao.joints.IGroupRessourceSchema;
import com.epsi.fiouzteam.fiouzoid.dao.joints.IGroupUser;
import com.epsi.fiouzteam.fiouzoid.model.Group;
import com.epsi.fiouzteam.fiouzoid.model.User;

import java.util.ArrayList;
import java.util.List;

public class GroupDao extends DbContentProvider
        implements IGroupSchema, IGroupDao {

    private static final String TAG = "GroupDao";
    private Cursor cursor;
    private ContentValues initialValues;
    public GroupDao(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public Group fetchById(int id) {
        final String selectionArgs[] = { String.valueOf(id) };
        final String selection = COLUMN_ID + " = ?";
        Group group = new Group();
        cursor = super.query(GROUP_TABLE, GROUP_COLUMNS, selection,
                selectionArgs, COLUMN_ID);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                group = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        List<User> groupUsers = Database.mUserDao.fetchAllByGroup(group.getId());
        group.setUsers(groupUsers);

        return group;
    }

    @Override
    public List<Group> fetchAllByUser(int userId)
    {
        List<Group> groupList = new ArrayList<Group>();
        String  url = "select g.* from UserGroup ug, \"Group\" g, User u where g.id = ug.idGroup and u.id = ug.idUser and u.id = ?";
        String[] args = { String.valueOf(userId) };

        cursor = super.rawQuery(url, args);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Group group = cursorToEntity(cursor);
                groupList.add(group);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return groupList;
    }

    public List<Group> fetchAllGroups() {
        List<Group> groupList = new ArrayList<Group>();
        cursor = super.query(GROUP_TABLE, GROUP_COLUMNS, null,
                null, COLUMN_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Group group = cursorToEntity(cursor);
                groupList.add(group);
                cursor.moveToNext();
            }
            cursor.close();
        }


        for (Group g : groupList)
        {
            if(g.getId() > 2)
                break;

            g.setUsers(Database.mUserDao.fetchAllByGroup(g.getId()));
        }

        return groupList;
    }

    public boolean addGroup(Group group) {
        // set values
        setContentValue(group);

        List<User> users = group.getUsers();
        if(users != null && !users.isEmpty()) // TODO test
        {
            String query = "insert into UserGroup" + " select ";
            boolean isFirst = true;
            for (User u :
                    users) {
                if(isFirst) {
                    query += u.getId() + " as idUser, " + group.getId() + " as idGroup";
                    isFirst = false;
                }
                else
                    query += " union all select " + u.getId() + ", " + group.getId();
            }
//            query += " ";

            Log.i(TAG, "raw query: " + query);
            super.rawQuery(query, null);
        }

        Log.i(TAG, "inserting group '" + group.getName() + "'");
        long res = super.insert(GROUP_TABLE, getContentValue());
        return res > 0;
    }

    @Override
    public boolean deleteAllGroups() {
        String query = "delete from " + GROUP_TABLE;
        cursor = super.rawQuery(query, null);

        // TODO handle return value
        return true;
    }

    @Override
    public boolean addGroups(List<Group> groups) {
        boolean ret = true;
            for (Group u : groups)
        {
            boolean val = addGroup(u);
            ret = ret != false && val;
        }

        return ret;
    }

    protected Group cursorToEntity(Cursor cursor) {

        Group group = new Group();

        int idIndex, nickNameIndex, emailIndex;

        if (cursor != null) {
            if (cursor.getColumnIndex(COLUMN_ID) != -1) {
                idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
                group.setId(cursor.getInt(idIndex));
            }
            if (cursor.getColumnIndex(COLUMN_NAME) != -1) {
                nickNameIndex = cursor.getColumnIndexOrThrow(
                        COLUMN_NAME);
                group.setName(cursor.getString(nickNameIndex));
            }
            if (cursor.getColumnIndex(COLUMN_DESCRIPTION) != -1) {
                emailIndex = cursor.getColumnIndexOrThrow(
                        COLUMN_DESCRIPTION);
                group.setDescription(cursor.getString(emailIndex));
            }

        }
        return group;
    }

    protected GroupRessources cursorToGroupRessources(Cursor cursor) {

        GroupRessources group = new GroupRessources();

        int idGroup, idType, qte;

        if (cursor != null) {
            if (cursor.getColumnIndex(IGroupRessourceSchema.COLUMN_GROUP) != -1) {
                idGroup = cursor.getColumnIndexOrThrow(IGroupRessourceSchema.COLUMN_GROUP);
                group.setIdGroup(cursor.getInt(idGroup));
            }
            if (cursor.getColumnIndex(IGroupRessourceSchema.COLUMN_TYPE) != -1) {
                idType = cursor.getColumnIndexOrThrow(
                        IGroupRessourceSchema.COLUMN_TYPE);
                group.setIdTypeRessource(cursor.getInt(idType));
            }
            if (cursor.getColumnIndex(IGroupRessourceSchema.COLUMN_QTE) != -1) {
                qte = cursor.getColumnIndexOrThrow(
                        IGroupRessourceSchema.COLUMN_QTE);
                group.setQuantite(cursor.getInt(qte));
            }

        }
        return group;
    }

    private void setContentValue(Group group) {
        initialValues = new ContentValues();
        if(group.getId() > 0)
            initialValues.put(COLUMN_ID, group.getId());

        initialValues.put(COLUMN_NAME, group.getName());
        initialValues.put(COLUMN_DESCRIPTION, group.getDescription());
    }

    private ContentValues getContentValue() {
        return initialValues;
    }

    public Group fetchByName(String groupName) {
        //TODO: correct here
        final int id = 0;
        final String selectionArgs[] = { String.valueOf(groupName) };
        final String selection = COLUMN_NAME + " = ?";
        Group group = new Group();
        cursor = super.query(GROUP_TABLE, GROUP_COLUMNS, selection,
                selectionArgs, COLUMN_ID);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                group = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        List<User> groupUsers = Database.mUserDao.fetchAllByGroup(group.getId());
        group.setUsers(groupUsers);

        return group;
    }

    public void deleteAllGroupRessources() {
        int count = mDb.delete(IGroupRessourceSchema.GROUP_RESSOURCE_TABLE, "1", null);
        Log.i(TAG, "Deleted " + String.valueOf(count) + " entries in table " + IGroupRessourceSchema.GROUP_RESSOURCE_TABLE);
    }

    public void deleteAllGroupUsers() {
        int count = mDb.delete(IGroupUser.GROUP_USER_TABLE, "1", null);
        Log.i(TAG, "Deleted " + String.valueOf(count) + " entries in table " + IGroupUser.GROUP_USER_TABLE);
    }

    public List<GroupRessources> fetchGroupRessources(int groupId) {
        /*
        String query = "select * from " + IGroupRessourceSchema.GROUP_RESSOURCE_TABLE +
                " where " + IGroupRessourceSchema.COLUMN_GROUP + " = " + String.valueOf(groupId);
        */

        // TODO finish
        List<GroupRessources> groupList = new ArrayList<>();
        final String selectionArgs[] = { String.valueOf(groupId) };
        final String selection = IGroupRessourceSchema.COLUMN_GROUP + " = ?";
        cursor = super.query(IGroupRessourceSchema.GROUP_RESSOURCE_TABLE , IGroupRessourceSchema.GROUP_COLUMNS, selection,
                selectionArgs, IGroupRessourceSchema.COLUMN_TYPE);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                GroupRessources group = cursorToGroupRessources(cursor);
                groupList.add(group);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return groupList;
    }

    public void addGroupRessources(List<GroupRessources> groups)
    {
        String query = "insert into " + IGroupRessourceSchema.GROUP_RESSOURCE_TABLE +
                    " select " + groups.get(0).getIdGroup() + " AS " + IGroupRessourceSchema.COLUMN_GROUP +
                    ", " + groups.get(0).getIdTypeRessource() + " AS " + IGroupRessourceSchema.COLUMN_TYPE +
                    ", " + groups.get(0).getQuantite() + " AS " + IGroupRessourceSchema.COLUMN_QTE + " ";

        boolean isFirst = true;
        for (GroupRessources gr : groups)
        {
            if(isFirst)
            {
                isFirst = false;
                continue;
            }

            query += "union all select " + gr.getIdGroup() + ", " +
                    gr.getIdTypeRessource() + ", " +
                    gr.getQuantite() + " ";
        }


        Log.i(TAG, "groupRessource query:\n" + query);
        super.rawQuery(query, null);
    }

    public void addGroupUsers(List<GroupUsers> groupUsers)
    {
        String query = "insert into " + IGroupUser.GROUP_USER_TABLE +
                " select " + groupUsers.get(0).getIdGroup() + " AS " + IGroupUser.COLUMN_GROUP +
                ", " + groupUsers.get(0).getIdUser() + " AS " + IGroupUser.COLUMN_USER + " ";

        boolean isFirst = true;
        for (GroupUsers gr : groupUsers)
        {
            if(isFirst)
            {
                isFirst = false;
                continue;
            }

            query += "union all select " + groupUsers.get(0).getIdGroup() + ", " +
                    gr.getIdUser() + " ";
        }

        Log.i(TAG, "groupUser query:\n" + query);
        super.rawQuery(query, null);
    }
}
