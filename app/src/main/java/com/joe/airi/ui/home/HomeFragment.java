package com.joe.airi.ui.home;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joe.airi.R;
import com.joe.airi.adapter.HomePagerAdapter;
import com.joe.airi.common.PersonalizedPageTransformer;
import com.joe.airi.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ViewPager vpHome = $(view, R.id.home_vp);
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(CityFragment.newInstance("", ""));
        fragmentList.add(CityFragment.newInstance("", ""));
        vpHome.setPageMargin(6);
        vpHome.setPageMarginDrawable(new ColorDrawable(Color.BLACK));
        vpHome.setAdapter(new HomePagerAdapter(getActivity().getSupportFragmentManager(), fragmentList));
        vpHome.setPageTransformer(false, new PersonalizedPageTransformer.ParallaxPageTransformer());
        return view;
    }


}
