package com.epsi.fiouzteam.fiouzoid;

import android.text.format.Time;
import android.util.Log;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Dju on 05/07/2016.
 */
public class LoggerSql {
    public static void Log(String msg, String criticity)
    {
        // %V,%G,%Y
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss a");
        String strDate = sdf.format(c.getTime()),
                line = strDate + "\t[" + (criticity != null ? criticity : "INFO") + '\t' + msg;
    }
}
