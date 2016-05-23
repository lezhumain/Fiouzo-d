package com.epsi.fiouzteam.fiouzoid.http;


import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Dju on 21/05/2016.
 */
public class Utils {
    private static final String TAG = "Utils";

    public static void Test()
    {
        HttpURLConnection urlConnection = null;
        try
        {
            URL url = new URL("http://www.android.com/");
            urlConnection = (HttpURLConnection) url.openConnection();


            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String recv = readStream(in);
            Log.i(TAG, '\t' + recv);
        }
        catch (Exception e)
        {
            Log.i(TAG, "\n=============================" +
                    "\n=============================" +
                    "\n" + e.getMessage() + " " + e.getClass().getName() +
                    "\n=============================" +
                    "\n=============================");
            e.printStackTrace();
        }
        finally
        {
            urlConnection.disconnect();
        }
    }

    public static String readStream(InputStream in) {
        byte[] b = new byte[1024];
        String ret = "";

        try {
            int count = in.read(b);
            ret = new String(b, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            ret = "";
        }

        return ret;
    }
}
