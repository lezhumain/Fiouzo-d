package com.epsi.fiouzteam.fiouzoid.http;

import android.os.AsyncTask;
import android.util.Log;

public class HttpHelper
{
    private static final String TAG = "HttpHelper";

    private String _result = "";
    private HttpTestTask task;
    private String _url;

    public HttpHelper(String url, TaskDelegate delegateForGet)
    {
        _url = url;
        //task = new HttpTestTask(delegateForGet);
        task = new HttpTestTask(url, delegateForGet);
    }

    public void Get()
    {
        _result = "";
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public String get_result() {
        return _result;
    }
}
