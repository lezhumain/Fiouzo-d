package com.epsi.fiouzteam.fiouzoid.http;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Dju on 21/05/2016.
 */
public class Utils {
    public void Test()
    {
        URL url = new URL("http://www.android.com/");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try
        {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String recv = readStream(in);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finallY
        {
            urlConnection.disconnect();
        }
    }

    private String readStream(InputStream in) {
        byte[] b = new byte[1024];

        try {
            int count = in.read(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
