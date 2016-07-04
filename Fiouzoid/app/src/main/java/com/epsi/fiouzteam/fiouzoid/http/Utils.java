package com.epsi.fiouzteam.fiouzoid.http;


import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cz.msebera.android.httpclient.HttpEntity;

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

    public static String entityToString(HttpEntity entity) {
        InputStream is = null;
        try {
            is = entity.getContent();
        } catch (IOException e) {
            return "";
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        StringBuilder str = new StringBuilder();

        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                str.append(line + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                //tough luck...
            }
        }
        return str.toString();
    }
}
