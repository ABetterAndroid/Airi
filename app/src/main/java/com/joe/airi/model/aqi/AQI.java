package com.joe.airi.model.aqi;

/**
 * Created by qiaorongzhu on 2015/1/16.
 */
public class AQI {

    CommonAQI citynow;
    Last lastTwoWeeks;

    public CommonAQI getCitynow() {
        return citynow;
    }

    public void setCitynow(CommonAQI citynow) {
        this.citynow = citynow;
    }

    public Last getLastTwoWeeks() {
        return lastTwoWeeks;
    }

    public void setLastTwoWeeks(Last lastTwoWeeks) {
        this.lastTwoWeeks = lastTwoWeeks;
    }
}
