package com.epsi.fiouzteam.fiouzoid.dao.user;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.epsi.fiouzteam.fiouzoid.dao.DbContentProvider;
import com.epsi.fiouzteam.fiouzoid.model.User;

import java.util.*;

import cz.msebera.android.httpclient.client.entity.EntityBuilder;

public class UserDao extends DbContentProvider
        implements IUserSchema, IUserDao {

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

    @Override
    public boolean deleteAllUsers() {
        return false;
    }

    protected User cursorToEntity(Cursor cursor) {

        User user = new User();

        int idIndex, nickNameIndex, emailIndex;

        if (cursor != null) {
            if (cursor.getColumnIndex(COLUMN_ID) != -1) {
                idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
                user.setId(cursor.getInt(idIndex));
            }
            if (cursor.getColumnIndex(COLUMN_NICK_NAME) != -1) {
                nickNameIndex = cursor.getColumnIndexOrThrow(
                        COLUMN_NICK_NAME);
                user.setNickName(cursor.getString(nickNameIndex));
            }
            if (cursor.getColumnIndex(COLUMN_EMAIL) != -1) {
                emailIndex = cursor.getColumnIndexOrThrow(
                        COLUMN_EMAIL);
                user.setEmail(cursor.getString(emailIndex));
            }

        }
        return user;
    }

    private void setContentValue(User user) {
        initialValues = new ContentValues();
        if(user.getId() > 0)
            initialValues.put(COLUMN_ID, user.getId());
        initialValues.put(COLUMN_NICK_NAME, user.getNickName());
        initialValues.put(COLUMN_EMAIL, user.getEmail());
    }

    private ContentValues getContentValue() {
        return initialValues;
    }

    public void deleteUsers() {
        cursor = super.rawQuery("delete from " + USER_TABLE, null);
    }
}
