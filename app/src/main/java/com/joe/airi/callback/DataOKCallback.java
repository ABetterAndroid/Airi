package com.joe.airi.callback;

import com.joe.airi.model.PM2_5;
import com.joe.airi.net.Result;

/**
 * Created by qiaorongzhu on 2015/1/13.
 */
public interface DataOKCallback {
    /**
     * 数据获取成功
     */
    void onDataOK(Result<PM2_5> mResult);
}
