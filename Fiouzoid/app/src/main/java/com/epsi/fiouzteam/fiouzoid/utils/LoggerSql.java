package com.epsi.fiouzteam.fiouzoid.utils;

import android.util.Log;

import com.epsi.fiouzteam.fiouzoid.dao.Database;

/**
 * Created by Dju on 05/07/2016.
 */
public class LoggerSql {
    private static final String TAG = "LoggerSql";

    public static void Log(String msg, String criticity, Boolean debug, Double bytesData)
    {
        if(debug == null)
            debug = false;

        // YYYY-MM-DD HH:MM:SS
        String dateNow = "now", dateParam = "localtime",
            query = "insert into Log (date, criticite, message, bytesUsed) values (" +
                    "datetime('now', 'localtime'), '" + (criticity == null ? "INFO" : criticity) + "', '" + msg + "', " +
                    bytesData + ")";


        //Log.i(TAG, "log query: " + query);
        if(debug)
            Log.i(TAG, '[' + criticity + "]\tlog query:\n\t" + msg);

        Database.Log(query);
    }
}
