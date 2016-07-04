package com.epsi.fiouzteam.fiouzoid.dao.user;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.epsi.fiouzteam.fiouzoid.dao.DbContentProvider;
import com.epsi.fiouzteam.fiouzoid.model.User;

import java.util.*;

public class UserDao extends DbContentProvider
        implements IUserSchema, IUserDao {

    private static final String TAG = "UserDao";
    private Cursor cursor;
    private ContentValues initialValues;
    public UserDao(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public User fetchById(int id) {
        final String selectionArgs[] = { String.valueOf(id) };
        final String selection = COLUMN_ID + " = ?";
        User user = new User();
        cursor = super.query(USER_TABLE, USER_COLUMNS, selection,
                selectionArgs, COLUMN_ID);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                user = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return user;
    }

    public List<User> fetchAllByGroup(int groupId)
    {
        List<User> userList = new ArrayList<User>();
        String  url = "select u.* from UserGroup ug, \"Group\" g, User u where g.id = ug.idGroup and u.id = ug.idUser and g.id = ?";
        String[] args = { String.valueOf(groupId) };

        cursor = super.rawQuery(url, args);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                User user = cursorToEntity(cursor);
                userList.add(user);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return userList;
    }

    public List<User> fetchAllUsers() {
        List<User> userList = new ArrayList<User>();
        cursor = super.query(USER_TABLE, USER_COLUMNS, null,
                null, COLUMN_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                User user = cursorToEntity(cursor);
                userList.add(user);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return userList;
    }

    public boolean addUser(User user) {
        // set values
        setContentValue(user);

        return super.insert(USER_TABLE, getContentValue()) > 0;
    }

    @Override
    public boolean addUsers(List<User> users) {
        boolean ret = true;
        for (User u : users)
        {
            ret = ret != false && addUser(u);
        }

        return ret;
    }

    public boolean addUsersToGroupe(List<User> users, int idGroup) {
        boolean ret = true, isFirst = true;

        String query = "insert into UserGroup select " +
                users.get(0).getId() + " as idUser, " +
                idGroup + " as idGroup ";

        for (User u : users)
        {
            if(isFirst)
            {
                isFirst = false;
                continue;
            }
            query += "union all select " + u.getId() + ", " + idGroup + ' ';
            //ret = ret != false && addUser(u);
        }

        Log.i(TAG, query);
        //super.rawQuery(query, null);
        String log = "\tinserts in UserGroup went ";
        if( super.execSql(query) )
            log += "good";
        else
            log += "wrong";
        Log.i(TAG, log);

        return ret;
    }

    @Override
    public boolean deleteAllUsers() {
        super.delete(USER_TABLE, null, null);
        return true;
    }

    protected User cursorToEntity(Cursor cursor) {

        User user = new User();

        int idIndex, nickNameIndex, firstNameIndex, lastNameIndex, isAdminIndex;

        if (cursor != null) {
            if (cursor.getColumnIndex(COLUMN_ID) != -1) {
                idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
                user.setId(cursor.getInt(idIndex));
            }
            if (cursor.getColumnIndex(COLUMN_USERNAME) != -1) {
                nickNameIndex = cursor.getColumnIndexOrThrow(
                        COLUMN_USERNAME);
                user.setUsername(cursor.getString(nickNameIndex));
            }
            if (cursor.getColumnIndex(COLUMN_FIRST_NAME) != -1) {
                firstNameIndex = cursor.getColumnIndexOrThrow(
                        COLUMN_FIRST_NAME);
                user.setFirstName(cursor.getString(firstNameIndex));
            }
            if (cursor.getColumnIndex(COLUMN_LAST_NAME) != -1) {
                lastNameIndex = cursor.getColumnIndexOrThrow(
                        COLUMN_LAST_NAME);
                user.setFirstName(cursor.getString(lastNameIndex));
            }
            if (cursor.getColumnIndex(COLUMN_IS_ADMIN) != -1) {
                isAdminIndex = cursor.getColumnIndexOrThrow(
                        COLUMN_IS_ADMIN);
                user.setAdmin(cursor.getInt(isAdminIndex) == 1 ? true : false);
            }

        }
        return user;
    }

    private void setContentValue(User user) {
        initialValues = new ContentValues();
        if(user.getId() > 0)
            initialValues.put(COLUMN_ID, user.getId());
        initialValues.put(COLUMN_USERNAME, user.getUsername());
        initialValues.put(COLUMN_FIRST_NAME, user.getFirstName());
        initialValues.put(COLUMN_LAST_NAME, user.getLastName());
        initialValues.put(COLUMN_IS_ADMIN, user.isAdmin());
    }

    private ContentValues getContentValue() {
        return initialValues;
    }

    public void deleteUsers() {
        cursor = super.rawQuery("delete from " + USER_TABLE, null);
    }
}
