package com.joe.airi.ui.home;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.enrique.stackblur.StackBlurManager;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.YLabels;
import com.joe.airi.R;
import com.joe.airi.callback.DataOKCallback;
import com.joe.airi.model.DataResult;
import com.joe.airi.model.aqi.AQI;
import com.joe.airi.model.aqi.Last;
import com.joe.airi.model.pm2_5.PM2_5;
import com.joe.airi.net.DataDownloader;
import com.joe.airi.ui.base.BaseFragment;
import com.joe.airi.utils.Constants;
import com.joe.airi.utils.Utils;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

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
    private LineChart mChart;
    private DataDownloader downloader;
    private int retryTimesPM;
    private int retryTimesAQI;
    private ImageView ivBackground;


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
            if (parent != null) {
                parent.removeView(view);
            }
            return view;
        }
        view = inflater.inflate(R.layout.fragment_city, container, false);
        tvCity = $(view, R.id.city);
        tvAQI = $(view, R.id.aqi);
        tvQuality = $(view, R.id.quality);
        ivBackground = $(view, R.id.background);

        blurBackground();

        mChart = $(view, R.id.chart);

        downloader = new DataDownloader(getActivity());
        downloader.getPMData("北京", this);
        downloader.getAQIData("北京", this);

        return view;
    }

    private void blurBackground() {
        new AsyncTask<Void, Void, Void>() {
            private Bitmap blurBmp;

            @Override
            protected Void doInBackground(Void... params) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.scene1);
                StackBlurManager stackBlurManager = new StackBlurManager(bitmap);
                blurBmp = stackBlurManager.processRenderScript(getActivity(), 50);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                ivBackground.setImageBitmap(blurBmp);
                FadeInBitmapDisplayer.animate(ivBackground,2000);
            }
        }.execute();
    }


    private void setupChart(LineChart chart, LineData data, int color) {

        Utils.fadeInVisible(chart);

        // if enabled, the chart will always start at zero on the y-axis
        chart.setStartAtZero(true);

        // disable the drawing of values into the chart
        chart.setDrawYValues(false);

        chart.setDrawBorder(false);

        // no description text
        chart.setDescription("");
        chart.setNoDataTextDescription("");

        // enable / disable grid lines
        chart.setDrawVerticalGrid(false);
        // mChart.setDrawHorizontalGrid(false);
        //
        // enable / disable grid background
        chart.setDrawGridBackground(false);
        chart.setGridColor(Color.WHITE & 0x70FFFFFF);
        chart.setGridWidth(1.25f);

        // enable touch gestures
        chart.setTouchEnabled(false);

        // enable scaling and dragging
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setBackgroundColor(color);

        // chart.setValueTypeface(mTf);

        // add data
        chart.setData(data);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(6f);
        l.setTextColor(Color.WHITE);

        YLabels y = chart.getYLabels();
        y.setTextColor(Color.WHITE);
        y.setLabelCount(4);

        XLabels x = chart.getXLabels();
        x.setTextColor(Color.WHITE);
        x.setPosition(XLabels.XLabelPosition.BOTTOM);

        // animate calls invalidate()...
        chart.animateX(1500);
    }

    private LineData getData(ArrayList<String> xVals, ArrayList<Entry> yVals) {

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "近期空气质量");
        // set1.setFillAlpha(110);
//        set1.setFillColor(Color.RED);

        set1.setLineWidth(1.75f);
        set1.setCircleSize(3f);
        set1.setColor(Color.WHITE);
        set1.setCircleColor(Color.WHITE);
        set1.setHighLightColor(Color.WHITE);

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        return data;
    }


    /**
     * 数据获取成功
     *
     * @param mResult
     */
    @Override
    public void onDataOK(DataResult mResult) {

        switch (mResult.getError_code()) {
            case DataResult.OK:

                switch (mResult.getResultType()) {
                    case Constants.RESULT_TYPE_PM2_5:
                        fillPMData(mResult);
                        retryTimesPM = 0;
                        break;
                    case Constants.RESULT_TYPE_AQI:
                        fillChartData(mResult);
                        retryTimesAQI = 0;
                        break;
                }
                break;
            case DataResult.NETWORK_INVALID:
                Utils.showToast(getActivity(), "无网络连接...");
                break;
            default:
                switch (mResult.getResultType()) {
                    case Constants.RESULT_TYPE_PM2_5:
                        if (retryTimesPM < 3) {

                            downloader.getPMData("北京", this);
                            retryTimesPM += 1;
                        } else {
                            Utils.showToast(getActivity(), "获取数据错误...");
                            retryTimesPM = 0;
                        }
                        break;
                    case Constants.RESULT_TYPE_AQI:
                        if (retryTimesAQI < 3) {

                            downloader.getAQIData("北京", this);
                            retryTimesAQI += 1;
                        } else {
                            Utils.showToast(getActivity(), "获取数据错误...");
                            retryTimesAQI = 0;
                        }
                        break;
                }


                break;
        }

    }

    private void fillPMData(DataResult mResult) {
        PM2_5 pm2_5 = (PM2_5) mResult.getResult().get(0);
        tvCity.setText(pm2_5.getCity());
        tvAQI.setText(pm2_5.getAQI());
        int aqi = Integer.valueOf(pm2_5.getAQI());
        if (aqi < 50) {
            tvQuality.setBackgroundResource(R.drawable.shape_aqi_green);
        } else if (aqi < 100) {
            tvQuality.setBackgroundResource(R.drawable.shape_aqi_yellow);
        } else {
            tvQuality.setBackgroundResource(R.drawable.shape_aqi_red);
        }
        tvQuality.setText(pm2_5.getQuality());
        Utils.fadeInVisible(tvQuality);
    }

    private void fillChartData(DataResult mResult) {
        AQI aqiData = (AQI) mResult.getResult().get(0);
        Last last = aqiData.getLastTwoWeeks();
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add(last.getLast1().getDate().substring(5));
        xVals.add(last.getLast2().getDate().substring(5));
        xVals.add(last.getLast3().getDate().substring(5));
        xVals.add(last.getLast4().getDate().substring(5));
        xVals.add(last.getLast5().getDate().substring(5));
        xVals.add(last.getLast6().getDate().substring(5));
        xVals.add(last.getLast7().getDate().substring(5));
        xVals.add(last.getLast8().getDate().substring(5));
        xVals.add(last.getLast9().getDate().substring(5));
        xVals.add(last.getLast10().getDate().substring(5));
        xVals.add(last.getLast11().getDate().substring(5));
        xVals.add(last.getLast12().getDate().substring(5));
        xVals.add(last.getLast13().getDate().substring(5));
        xVals.add(last.getLast14().getDate().substring(5));
        xVals.add(last.getLast15().getDate().substring(5));
        xVals.add(last.getLast16().getDate().substring(5));
        xVals.add(last.getLast17().getDate().substring(5));
        xVals.add(last.getLast18().getDate().substring(5));
        xVals.add(last.getLast19().getDate().substring(5));
        xVals.add(last.getLast20().getDate().substring(5));
        xVals.add(last.getLast21().getDate().substring(5));
        xVals.add(last.getLast22().getDate().substring(5));
        xVals.add(last.getLast23().getDate().substring(5));
        xVals.add(last.getLast24().getDate().substring(5));
        xVals.add(last.getLast25().getDate().substring(5));
        xVals.add(last.getLast26().getDate().substring(5));
        xVals.add(last.getLast27().getDate().substring(5));

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        yVals.add(new Entry(Float.valueOf(last.getLast1().getAQI()), 0));
        yVals.add(new Entry(Float.valueOf(last.getLast2().getAQI()), 1));
        yVals.add(new Entry(Float.valueOf(last.getLast3().getAQI()), 2));
        yVals.add(new Entry(Float.valueOf(last.getLast4().getAQI()), 3));
        yVals.add(new Entry(Float.valueOf(last.getLast5().getAQI()), 4));
        yVals.add(new Entry(Float.valueOf(last.getLast6().getAQI()), 5));
        yVals.add(new Entry(Float.valueOf(last.getLast7().getAQI()), 6));
        yVals.add(new Entry(Float.valueOf(last.getLast8().getAQI()), 7));
        yVals.add(new Entry(Float.valueOf(last.getLast9().getAQI()), 8));
        yVals.add(new Entry(Float.valueOf(last.getLast10().getAQI()), 9));
        yVals.add(new Entry(Float.valueOf(last.getLast11().getAQI()), 10));
        yVals.add(new Entry(Float.valueOf(last.getLast12().getAQI()), 11));
        yVals.add(new Entry(Float.valueOf(last.getLast13().getAQI()), 12));
        yVals.add(new Entry(Float.valueOf(last.getLast14().getAQI()), 13));
        yVals.add(new Entry(Float.valueOf(last.getLast15().getAQI()), 14));
        yVals.add(new Entry(Float.valueOf(last.getLast16().getAQI()), 15));
        yVals.add(new Entry(Float.valueOf(last.getLast17().getAQI()), 16));
        yVals.add(new Entry(Float.valueOf(last.getLast18().getAQI()), 17));
        yVals.add(new Entry(Float.valueOf(last.getLast19().getAQI()), 18));
        yVals.add(new Entry(Float.valueOf(last.getLast20().getAQI()), 19));
        yVals.add(new Entry(Float.valueOf(last.getLast21().getAQI()), 20));
        yVals.add(new Entry(Float.valueOf(last.getLast22().getAQI()), 21));
        yVals.add(new Entry(Float.valueOf(last.getLast23().getAQI()), 22));
        yVals.add(new Entry(Float.valueOf(last.getLast24().getAQI()), 23));
        yVals.add(new Entry(Float.valueOf(last.getLast25().getAQI()), 24));
        yVals.add(new Entry(Float.valueOf(last.getLast26().getAQI()), 25));
        yVals.add(new Entry(Float.valueOf(last.getLast27().getAQI()), 26));

        setupChart(mChart, getData(xVals, yVals), Color.TRANSPARENT);
    }
}
