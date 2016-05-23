package com.epsi.fiouzteam.fiouzoid.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.epsi.fiouzteam.fiouzoid.model.User;

import java.util.*;

public class UserDao extends DbContentProvider
        implements IUserSchema, IUserDao {

    private Cursor cursor;
    private ContentValues initialValues;
    public UserDao(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public User fetchUserById(int id) {
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
        try {
            return super.insert(USER_TABLE, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex){
            Log.w("Database", ex.getMessage());
            return false;
        }
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

        int idIndex;
        int firstNameIndex;
        int lastNameIndex;
        int nickNameIndex;

        if (cursor != null) {
            if (cursor.getColumnIndex(COLUMN_ID) != -1) {
                idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
                user.set_id(cursor.getInt(idIndex));
            }
            if (cursor.getColumnIndex(COLUMN_FIRST_NAME) != -1) {
                firstNameIndex = cursor.getColumnIndexOrThrow(
                        COLUMN_FIRST_NAME);
                user.set_firstName(cursor.getString(firstNameIndex));
            }
            if (cursor.getColumnIndex(COLUMN_LAST_NAME) != -1) {
                lastNameIndex = cursor.getColumnIndexOrThrow(
                        COLUMN_LAST_NAME);
                user.set_lastName(cursor.getString(lastNameIndex));
            }
            if (cursor.getColumnIndex(COLUMN_NICK_NAME) != -1) {
                nickNameIndex = cursor.getColumnIndexOrThrow(
                        COLUMN_NICK_NAME);
                user.set_nickName(cursor.getString(nickNameIndex));
            }



        }
        return user;
    }

    private void setContentValue(User user) {
        initialValues = new ContentValues();
        initialValues.put(COLUMN_ID, user.get_id());
        initialValues.put(COLUMN_FIRST_NAME, user.get_firstName());
        initialValues.put(COLUMN_LAST_NAME, user.get_lastName());
        initialValues.put(COLUMN_NICK_NAME, user.get_nickName());
    }

    private ContentValues getContentValue() {
        return initialValues;
    }

}
