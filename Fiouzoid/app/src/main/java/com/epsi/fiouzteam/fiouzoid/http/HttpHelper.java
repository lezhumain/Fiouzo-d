package com.epsi.fiouzteam.fiouzoid.http;

import android.os.AsyncTask;

import java.util.concurrent.ExecutionException;

public class HttpHelper
{
    private static final String TAG = "HttpHelper";

    private String _result = "";
    private HttpTestTask task;
    //private HttpPostTask postTask;
    private String _url;

    public HttpHelper(String url, TaskDelegate delegateForGet)
    {
        _url = url;
        //postTask = new HttpPostTask(url, delegateForGet);
        task = new HttpTestTask(url, delegateForGet);
    }

    public String Get()
    {
        _result = "";
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "get");

        try {
            return task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String Post(final String data)
    {
        _result = "";
        //postTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "post", data);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "post", data);

        try {
            String ret = task.get();
            return ret;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String get_result() {
        return _result;
    }
}
