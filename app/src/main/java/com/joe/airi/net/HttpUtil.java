package com.joe.airi.net;

import android.content.Context;

import com.joe.airi.model.pm2_5.PM2_5;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by qiaorongzhu on 2015/1/13.
 */
public class HttpUtil {
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(Context context, String url, RequestParams params, BaseJsonHttpResponseHandler<PM2_5> handler){

        client.get(context, url, params, handler);

    }

}
