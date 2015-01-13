package com.joe.airi.common;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.joe.airi.R;

/**
 * Created by qiaorongzhu on 2015/1/13.
 */
public class PersonalizedPageTransformer {

    public static class ParallaxPageTransformer implements ViewPager.PageTransformer {


        public void transformPage(View view, float position) {
            View dummyView= view.findViewById(R.id.page);
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(1);
            } else if (position <= 1) { // [-1,1]
                dummyView.setTranslationX(-position * (pageWidth / 2));
            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(1);
            }
        }
    }

}
