package com.joe.airi.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by qiaorongzhu on 2015/1/13.
 */
public class HomePagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> dataList;

    public HomePagerAdapter(FragmentManager fm, List<Fragment> dataList) {
        super(fm);
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return dataList.get(position);
    }
}
