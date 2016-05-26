package com.epsi.fiouzteam.fiouzoid.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.epsi.fiouzteam.fiouzoid.dao.group.GroupDao;
import com.epsi.fiouzteam.fiouzoid.dao.group.IGroupSchema;
import com.epsi.fiouzteam.fiouzoid.dao.user.IUserSchema;
import com.epsi.fiouzteam.fiouzoid.dao.user.UserDao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


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
    //private static String database_path = "";
    private static final String database_path = "/mnt/sdcard/fiouzoid/";



   public Database open() throws SQLException {
       //mDbHelper = new DatabaseHelper(mContext);
       mDbHelper = new DatabaseHelper(mContext);
       SQLiteDatabase mDb = mDbHelper.getWritableDatabase();

       //database_path = (mContext.getDatabasePath(DATABASE_NAME)).getAbsolutePath();
       //database_path = mDb.getPath();
       //cpDb();

       mUserDao = new UserDao(mDb);
       mGroupDao = new GroupDao(mDb);
       return this;
   }

    public static void cpDb()
    {
        Log.d(TAG, "cpDb: " + database_path + '\n');

        String inPath = database_path;
        String outPath = "/mnt/sdcard/fiouzteam/";
        File f=new File(inPath), fileO = new File(outPath);
        FileInputStream fis=null;
        FileOutputStream fos=null;

        try
        {
            fis=new FileInputStream(f);
            if(fileO.createNewFile() == false) {
                Log.i(TAG, "Error creating file");
                //Log.i(TAG, "DB dump ERROR");
                //return;
            }
            fileO.setReadable(true, false);
            fileO.setWritable(true, false);

            //fos=new FileOutputStream(outPath + DATABASE_NAME);
            fos=new FileOutputStream(fileO);
            while(true)
            {
                int i=fis.read();
                if(i!=-1)
                {fos.write(i);}
                else
                {break;}
            }
            fos.flush();
            Log.i(TAG, "DB dump OK");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Log.i(TAG, "DB dump ERROR");
        }
        finally
        {
            try
            {
                if(fos != null)
                    fos.close();
                if(fis != null)
                    fis.close();
            }
            catch(IOException ioe)
            {}
        }
    }

    public void close() {
       mDbHelper.close();
   }

   public Database(Context context) {
       this.mContext = context;
   }


   private static class DatabaseHelper extends SQLiteOpenHelper {
       DatabaseHelper(Context context) {
           super(context, database_path + DATABASE_NAME, null, DATABASE_VERSION);
       }

       @Override
       public void onCreate(SQLiteDatabase db)
       {
           db.execSQL(IUserSchema.USER_TABLE_CREATE);
           //db.execSQL(IGroupSchema.GROUP_TABLE_CREATE);
       }

       @Override
       public void onUpgrade(SQLiteDatabase db, int oldVersion,
          int newVersion) {
           Log.w(TAG, "Upgrading database from version "
                + oldVersion + " to "
                + newVersion + " which destroys all old data");

           db.execSQL("DROP TABLE IF EXISTS " 
                + IUserSchema.USER_TABLE);
           db.execSQL("DROP TABLE IF EXISTS "
                   + IGroupSchema.GROUP_TABLE);
           onCreate(db);

       }
   }

}
