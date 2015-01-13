package com.joe.airi.ui.base;

import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * Created by qiaorongzhu on 2015/1/12.
 */
public class BaseActivity extends FragmentActivity {

    //findViewById
    public <T extends View> T $(int id){
        return (T)super.findViewById(id);
    }

}
