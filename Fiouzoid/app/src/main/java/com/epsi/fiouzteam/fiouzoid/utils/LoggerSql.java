package com.epsi.fiouzteam.fiouzoid.utils;

import android.util.Log;

import com.epsi.fiouzteam.fiouzoid.dao.Database;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Dju on 05/07/2016.
 */
public class LoggerSql {
    private static final String TAG = "LoggerSql";

    public static void Log(String msg, String criticity, Boolean debug)
    {
        if(debug == null)
            debug = false;

        // YYYY-MM-DD HH:MM:SS
        String dateNow = "now", dateParam = "localtime",
            query = "insert into Log (date, criticite, message) values (" +
                    "datetime('now', 'localtime'), '" + (criticity == null ? "INFO" : criticity) + "', '" + msg + "')";


        //Log.i(TAG, "log query: " + query);
        if(debug)
            Log.i(TAG, '[' + criticity + "]\tlog query:\n\t" + msg);

        Database.Log(query);
    }
}
