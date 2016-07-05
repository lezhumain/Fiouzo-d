package com.epsi.fiouzteam.fiouzoid.http;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * Created by Dju on 23/05/2016.
 */
public class HttpTestTask extends AsyncTask<String, Void, String>
{
    private static final String TAG = "HttpTestTask";
    private String _url = "http://jsonplaceholder.typicode.com/posts/1";

    private TaskDelegate _delegate;

    public HttpTestTask(TaskDelegate delegate)
    {
        _delegate = delegate;
    }

    public HttpTestTask(String url, TaskDelegate delegate)
    {
        _url = url;
        _delegate = delegate;
    }

    @Override
    protected void onPreExecute() {}

    @Override
    protected String doInBackground(String... params) {
        String realUrl = _url.replace("$QUERY$", "Android"),
            method = "", result = "";
        Log.i(TAG, "old: " + _url + "\nreal: " + realUrl); // TODO check replace

        if (params.length > 0)
            method = params[0];

        switch (method)
        {
            case "get":
                result = requestContent(realUrl);
                break;
            case "post":
//                String data = params[1];
//                result = postContent(realUrl, data);
                String data = params[1],
                        fullUrl = realUrl + '?' + data;
                Log.i(TAG, "fullUrl:\n\t" + fullUrl);
                result = postContent(fullUrl, null);
                break;
            default:
                Log.d(TAG, "ERROR: in default case...");
        }

        //return requestContent(realUrl);
        Log.i(TAG, "HTTP RESPONSE:\n\t" + result);
        return result;
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

        //result = handleJson(result);
        return result;
    }

/*
    public static void PostContent(String  url, String data)
    {
        HttpClient httpclient = new DefaultHttpClient();
        String result = null;
        //HttpGet httpget = new HttpGet(url);
        HttpPost httppost = new HttpPost(url);
        HttpResponse response = null;
        //InputStream instream = null;

        try
        {
            response = httpclient.execute(httppost);
            StatusLine sl = response.getStatusLine();

            int code = sl.getStatusCode();
            Log.i(TAG, "\t post executed: code " + String.valueOf(code));
        } catch (Exception e)
        {
            Log.i(TAG, "\t post error:");
            Log.i(TAG, "\t\t" + e.getMessage());
        }
        //finally {}
    }
*/
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

    private String postContent(String  url, String data)
    {
        HttpClient httpclient = new DefaultHttpClient();
        String result;
        HttpPost httppost = new HttpPost(url);

        HttpResponse response;

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<>(2);
            nameValuePairs.add(new BasicNameValuePair("id", "12345"));
            nameValuePairs.add(new BasicNameValuePair("stringdata", "Hi"));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            response = httpclient.execute(httppost);

            result = String.valueOf(response.getStatusLine().getStatusCode()) + "\n" +
                    Utils.entityToString( response.getEntity() );

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            result = "";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            result = "";
        }

        return result;
    }

/*
    private static String handleJson(String data)
    {
        String msg = "";

        try
        {
            JSONObject json = new JSONObject(data);
            //int userID = (int)json.get("userId");

            int imsg = (int) json.get("userId");
            msg = String.valueOf(imsg);

        }
        catch (JSONException e)
        {
            //msg = e.getMessage() + '\n';
        }

        return msg;
    }


    @Override
    protected void onPostExecute(String res)
    {
        String msg = handleJson(res);

        Log.i(TAG, "\t\t" + msg + '\n');
        //_delegate.notifyAll();
    }
    */
}
