package com.epsi.fiouzteam.fiouzoid.dao.group;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.epsi.fiouzteam.fiouzoid.dao.DbContentProvider;
import com.epsi.fiouzteam.fiouzoid.model.Group;

import java.util.ArrayList;
import java.util.List;

public class GroupDao extends DbContentProvider
        implements IGroupSchema, IGroupDao {

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

        return group;
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

        return groupList;
    }

    public boolean addGroup(Group group) {
        // set values
        setContentValue(group);
        try {
            return super.insert(GROUP_TABLE, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex){
            Log.w("Database", ex.getMessage());
            return false;
        }
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
        initialValues.put(COLUMN_NAME, group.getName());
        initialValues.put(COLUMN_DESCRIPTION, group.getDescription());
    }

    private ContentValues getContentValue() {
        return initialValues;
    }

}
