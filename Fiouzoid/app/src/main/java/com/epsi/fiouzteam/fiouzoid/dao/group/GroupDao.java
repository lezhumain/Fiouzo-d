package com.epsi.fiouzteam.fiouzoid.dao.group;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.epsi.fiouzteam.fiouzoid.dao.Database;
import com.epsi.fiouzteam.fiouzoid.dao.DbContentProvider;
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

        /*
        for (Group g : groupList)
            g.setUsers(Database.mUserDao.fetchAllByGroup(g.getId()));
        */

        return groupList;
    }

    public boolean addGroup(Group group) {
        // set values
        setContentValue(group);

        List<User> users = group.getUsers();
        if(users != null) // TODO test
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

        return super.insert(GROUP_TABLE, getContentValue()) > 0;
    }

    @Override
    public boolean addGroups(List<Group> groups) {
        boolean ret = true;
        for (Group u : groups)
        {
            ret = ret != false && addGroup(u);
        }

        return ret;
    }

    @Override
    public boolean deleteAllGroups() {
        return false;
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

}
