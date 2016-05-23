package com.epsi.fiouzteam.fiouzoid.http;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

/**
 * Created by Dju on 23/05/2016.
 */
public class HttpTestTask extends AsyncTask<String, Void, String>
{
    private static final String TAG = "HttpTestTask";
    private static final String URL = "http://jsonplaceholder.typicode.com/posts/1";

    @Override
    protected void onPreExecute() {}

    @Override
    protected String doInBackground(String... params) {
        String realUrl = URL.replace("$QUERY$", "Android");
        return requestContent(realUrl);
    }

    public static String requestContent(String url)
    {
        HttpClient httpclient = new DefaultHttpClient();
        String result = null;
        HttpGet httpget = new HttpGet(url);
        HttpResponse response = null;
        InputStream instream = null;

        try {
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                instream = entity.getContent();
                result = convertStreamToString(instream);
            }

        } catch (Exception e) {
            // manage exceptions
        } finally {
            if (instream != null) {
                try {
                    instream.close();
                } catch (Exception exc) {

                }
            }
        }

        return result;
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }

        return sb.toString();
    }

    @Override
    protected void onPostExecute(String res)
    {
        String msg;
        try
        {
            JSONObject json = new JSONObject(res);
            //int userID = (int)json.get("userId");

            int imsg = (int) json.get("userId");
            msg = String.valueOf(imsg);

        }
        catch (JSONException e)
        {
            msg = e.getMessage() + '\n';
        }

        Log.i(TAG, "\t\t" + res + '\n' + msg + '\n');
    }
}
