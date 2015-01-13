package com.joe.airi.ui.home;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.enrique.stackblur.StackBlurManager;
import com.joe.airi.R;
import com.joe.airi.callback.DataOKCallback;
import com.joe.airi.model.PM2_5;
import com.joe.airi.net.DataDownloader;
import com.joe.airi.net.Result;
import com.joe.airi.ui.base.BaseFragment;
import com.joe.airi.utils.Util;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CityFragment extends BaseFragment implements DataOKCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private TextView tvCity;
    private TextView tvAQI;
    private TextView tvQuality;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CityFragment newInstance(String param1, String param2) {
        CityFragment fragment = new CityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null ) {
                parent.removeView(view);
            }
            return view;
        }
        view = inflater.inflate(R.layout.fragment_city, container, false);
        tvCity = $(view, R.id.city);
        tvAQI = $(view, R.id.aqi);
        tvQuality = $(view, R.id.quality);
        ImageView ivBackground=$(view, R.id.background);
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.scene1);
        StackBlurManager stackBlurManager = new StackBlurManager(bitmap);
        ivBackground.setImageBitmap(stackBlurManager.processRenderScript(getActivity(), 50));


        new DataDownloader(getActivity()).getPMData("北京", this);

        return view;
    }


    /**
     * 数据获取成功
     *
     * @param mResult
     */
    @Override
    public void onDataOK(Result<PM2_5> mResult) {
        switch (mResult.getErrorCode()) {
            case Result.OK:
                PM2_5 pm2_5=mResult.getResult();
                tvCity.setText(pm2_5.getCity());
                tvAQI.setText(pm2_5.getAQI());
                int aqi = Integer.valueOf(pm2_5.getAQI());
                if (aqi < 50) {
                    tvQuality.setBackgroundResource(R.drawable.shape_aqi_green);
                }else if (aqi < 100) {
                    tvQuality.setBackgroundResource(R.drawable.shape_aqi_yellow);
                } else {
                    tvQuality.setBackgroundResource(R.drawable.shape_aqi_red);
                }
                tvQuality.setText(pm2_5.getQuality());

                break;
            case Result.NETWORK_INVALID:
                Util.showToast(getActivity(), "无网络连接...");
                break;
            default:
                Util.showToast(getActivity(), "获取数据错误...");
                break;
        }
    }
}
