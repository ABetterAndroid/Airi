package com.joe.airi.ui.base;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by qiaorongzhu on 2015/1/12.
 */
public class BaseFragment extends Fragment {

    //findViewById
    public <T extends View> T $(View parent, int id){
        return (T)parent.findViewById(id);
    }

}
