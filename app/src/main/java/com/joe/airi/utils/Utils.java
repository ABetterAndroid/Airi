package com.joe.airi.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

/**
 * Created by qiaorongzhu on 2015/1/13.
 */
public class Utils {
    /**
     * 判断网络连接
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    /**
     * 弹出提示
     * @param context
     * @param toastString
     */
    public static void showToast(Context context, String toastString) {

        Toast.makeText(context, toastString, Toast.LENGTH_SHORT).show();

    }

    /**
     * fade in
     * @param view
     */
    public static void fadeInVisible(View view) {
        view.setAlpha(0f);
        view.animate().alpha(1.0f).setDuration(800).start();
        view.setVisibility(View.VISIBLE);
    }

}
