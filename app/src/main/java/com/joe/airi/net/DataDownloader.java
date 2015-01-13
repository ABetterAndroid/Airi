package com.joe.airi.net;

import android.content.Context;

import com.google.gson.Gson;
import com.joe.airi.callback.DataOKCallback;
import com.joe.airi.model.PM2_5;
import com.joe.airi.utils.Constants;
import com.joe.airi.utils.Util;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by qiaorongzhu on 2015/1/13.
 */
public class DataDownloader {

    private Context context;

    public DataDownloader(Context context) {
        this.context=context;
    }

    public void getPMata(String city, final DataOKCallback mCallBack) {

        if (!Util.isNetworkConnected(context)){
            mCallBack.onDataOK(new Result<PM2_5>(null, Result.NETWORK_INVALID));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("city", city);
        params.put("key", Constants.APP_KEY);
        HttpUtil.get(context, Constants.URL_PM2_5, params, new BaseJsonHttpResponseHandler<PM2_5>() {

            Result<PM2_5> mResult;

            @Override
            public void onSuccess(int i, Header[] headers, String s, PM2_5 pm2_5) {
                if (pm2_5 == null) {
                    mCallBack.onDataOK(new Result<PM2_5>(pm2_5, Result.JSON_DATA_ERROR));
                } else {

                    mCallBack.onDataOK(new Result<PM2_5>(pm2_5, Result.OK));
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, Throwable throwable, String s, PM2_5 pm2_5) {
                mCallBack.onDataOK(new Result<PM2_5>(null, Result.UNKNOWN_ERROR));

            }

            @Override
            protected PM2_5 parseResponse(String s, boolean b) throws Throwable {
                JSONObject object=new JSONObject(s);
                int errorCode=object.getInt("error_code");
                if (errorCode != 0) {
                    return null;
                } else {
                    Gson gson=new Gson();
                    String jsonStr=object.getJSONArray("result").get(0).toString().replace("PM2.5", "PM2_5");
                    PM2_5 pm2_5=gson.fromJson(jsonStr, PM2_5.class);
                    return pm2_5;
                }
            }
        });

    }
}
