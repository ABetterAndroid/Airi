package com.joe.airi.ui.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.joe.airi.R;
import com.joe.airi.adapter.HomePagerAdapter;
import com.joe.airi.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager vpHome=$(R.id.home_vp);
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(CityFragment.newInstance("", ""));
        vpHome.setAdapter(new HomePagerAdapter(getSupportFragmentManager(), fragmentList));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
