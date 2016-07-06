package com.epsi.fiouzteam.fiouzoid.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.epsi.fiouzteam.fiouzoid.utils.LogMsg;

/**
 * Created by Dju on 06/07/2016.
 */
public class LogDao extends DbContentProvider{
    private static final String TAG = "LogDao";

    public LogDao(SQLiteDatabase db) {
        super(db);
    }

    public void Log(String logQuery)
    {
        Log.i(TAG, "query: " + logQuery);
        mDb.execSQL(logQuery);
    }

    @Override
    protected LogMsg cursorToEntity(Cursor cursor) {
        return null;
    }
}
