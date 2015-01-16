package com.joe.airi.net;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joe.airi.callback.DataOKCallback;
import com.joe.airi.model.DataResult;
import com.joe.airi.model.pm2_5.PM2_5;
import com.joe.airi.model.aqi.AQI;
import com.joe.airi.utils.Constants;
import com.joe.airi.utils.Utils;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

/**
 * Created by qiaorongzhu on 2015/1/13.
 */
public class DataDownloader {

    private Context context;

    public DataDownloader(Context context) {
        this.context=context;
    }

    public void getPMData(String city, final DataOKCallback mCallBack) {

        if (!Utils.isNetworkConnected(context)){
            mCallBack.onDataOK(new DataResult<PM2_5>(null, DataResult.NETWORK_INVALID));
            return;
        }

        Parameters params=new Parameters();
        params.add("city", city);
        JuheData.executeWithAPI(Constants.API_ID_AQI, Constants.URL_PM2_5, JuheData.GET, params, new DataCallBack() {
            @Override
            public void resultLoaded(int i, String s, String s2) {
                DataResult<PM2_5> dataResult = new Gson().fromJson(s2, new TypeToken<DataResult<PM2_5>>() {
                }.getType());
                dataResult.setResultType(Constants.RESULT_TYPE_PM2_5);
                mCallBack.onDataOK(dataResult);

            }
        });
        /*RequestParams params = new RequestParams();
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
        });*/

    }


    /**
     * 获取空气质量数据
     * @param city
     * @param mCallBack
     */
    public void getAQIData(String city, final DataOKCallback mCallBack) {

        if (!Utils.isNetworkConnected(context)){
            mCallBack.onDataOK(new DataResult<AQI>(null, DataResult.NETWORK_INVALID));
            return;
        }

        Parameters params=new Parameters();
        params.add("city", city);
        JuheData.executeWithAPI(Constants.API_ID_AQI, Constants.URL_AQI, JuheData.GET, params, new DataCallBack() {

            @Override
            public void resultLoaded(int i, String s, String s2) {
                DataResult<AQI> dataResult = new Gson().fromJson(s2, new TypeToken<DataResult<AQI>>() {
                }.getType());
                dataResult.setResultType(Constants.RESULT_TYPE_AQI);
                mCallBack.onDataOK(dataResult);

            }
        });

    }
}
