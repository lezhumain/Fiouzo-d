package com.epsi.fiouzteam.fiouzoid.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.epsi.fiouzteam.fiouzoid.dao.group.GroupDao;
import com.epsi.fiouzteam.fiouzoid.dao.user.UserDao;


public class Database
{
    private static final String TAG = "MyDatabase";
    private static final String DATABASE_NAME = "fiouzoid.db";
    private DatabaseHelper mDbHelper;
    // Increment DB Version on any schema change
    private static final int DATABASE_VERSION = 1;
    private final Context mContext;
    public static UserDao mUserDao;
    public static GroupDao mGroupDao;
    private static LogDao mLogDao;
    private static final String database_path = "/mnt/sdcard/fiouzoid/";



    public Database open() throws SQLException {
        mDbHelper = new DatabaseHelper(mContext);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        UpgradeDb(db);

        mUserDao = new UserDao(db);
        mGroupDao = new GroupDao(db);
        mLogDao = new LogDao(db);
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public Database(Context context) {
        this.mContext = context;
    }


    public static void Log(String logQuery)
    {
        mLogDao.Log(logQuery);
    }

    // Ne fonctionne pas, incomprehensible
    public static void CreateDb(SQLiteDatabase db)
    {
        Log.i(TAG, "\tCreateDb started");

        String query = "CREATE TABLE IF NOT EXISTS User(\n" +
                "\tid \t\t\tINTEGER NOT NULL ,\n" +
                "\tusername\tVARCHAR NOT NULL ,\n" +
                "\tfirstName\tVARCHAR NOT NULL ,\n" +
                "\tlastName\tVARCHAR NOT NULL ,\n" +
                "\tisAdmin \tBOOLEAN NOT NULL ,\n" +
                "\tPRIMARY KEY (id)\n" +
                ");";
        db.execSQL(query);

        query = "CREATE TABLE IF NOT EXISTS \"Group\"(\n" +
                "\tid           INTEGER NOT NULL ,\n" +
                "\tname         VARCHAR NOT NULL ,\n" +
                "\tdescription  TEXT ,\n" +
                "\tPRIMARY KEY (id)\n" +
                ");";
        db.execSQL(query);

        query = "CREATE TABLE IF NOT EXISTS TypeRessource(\n" +
                "\tid    INTEGER NOT NULL ,\n" +
                "\tname  VARCHAR NOT NULL ,\n" +
                "\tPRIMARY KEY (id)\n" +
                ");";
        db.execSQL(query);

        query = "CREATE TABLE IF NOT EXISTS UserGroup(\n" +
                "\tidUser   INTEGER NOT NULL ,\n" +
                "\tidGroup  INTEGER NOT NULL ,\n" +
                "\tPRIMARY KEY (idUser,idGroup) ,\n" +
                "\t\n" +
                "\tFOREIGN KEY (idUser) REFERENCES User(id),\n" +
                "\tFOREIGN KEY (idGroup) REFERENCES \"Group\"(id)\n" +
                ");";
        db.execSQL(query);

        query = "CREATE TABLE IF NOT EXISTS Log(\n" +
                "\t\"id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL,\n" +
                "\t\"date\" DATETIME NOT NULL,\n" +
                "\t\"criticite\" VARCHAR NOT NULL  DEFAULT 'INFO',\n" +
                "\t\"message\" TEXT NOT NULL,\n" +
                "\t\"bytesUsed\" DOUBLE\n" +
                ");";
        db.execSQL(query);

        query = "CREATE TABLE IF NOT EXISTS GroupRessource(\n" +
                "\tquantite  INTEGER NOT NULL ,\n" +
                "\tressource VARCHAR NOT NULL ,\n" +
                "\tidRessource INTEGER NOT NULL ,\n" +
                "\tidGroup  INTEGER NOT NULL ,\n" +
                "\tPRIMARY KEY (idRessource,idGroup) ,\n" +
                "\t\n" +
                "\tFOREIGN KEY (idRessource) REFERENCES TypeRessource(id),\n" +
                "\tFOREIGN KEY (idGroup) REFERENCES \"Group\"(id)\n" +
                ");";
        db.execSQL(query);

        Log.i(TAG, "Now adding UserGroup table");


        Log.i(TAG, "\tCreateDb done");
    }

    public static void UpgradeDb(SQLiteDatabase db) {
        Log.i(TAG, "\tUpgradeDb started");
        final String query = "drop table if exists UserGroup;\n" +
                "drop table if exists GroupRessource;\n" +
                "drop table if exists TypeRessource;\n" +
                "drop table if exists User;\n" +
                "drop table if exists Log;\n" +
                "drop table if exists \"Group\";";

        db.execSQL(query);
        Log.i(TAG, "\tUpgradeDb done");

        CreateDb(db);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, database_path + DATABASE_NAME, null, DATABASE_VERSION);
        }



        @Override
        public void onCreate(SQLiteDatabase db)
        {
            CreateDb(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
            Log.w(TAG, "Upgrading database from version "
                    + oldVersion + " to "
                    + newVersion + " which destroys all old data");

            UpgradeDb(db);
        }


    }

}