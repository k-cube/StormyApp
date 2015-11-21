package com.keivannorouzi.stormy.weather;

import com.keivannorouzi.stormy.R;

/**
 * Created by keivannorouzi on 15-11-01.
 */
public class Forecast {

    private Current mCurrent;
    private Hourly[] mHourlies;
    private Daily[] mDailies;

    public Current getCurrent() {
        return mCurrent;
    }

    public void setCurrent(Current current) {
        mCurrent = current;
    }

    public Hourly[] getHourlies() {
        return mHourlies;
    }

    public void setHourlies(Hourly[] hourlies) {
        mHourlies = hourlies;
    }

    public Daily[] getDailies() {
        return mDailies;
    }

    public void setDailies(Daily[] dailies) {
        mDailies = dailies;
    }

    public static int getIconId(String iconString) {//clear-day, clear-night, rain, snow, sleet, wind, fog, cloudy, partly-cloudy-day, or partly-cloudy-night.
        int mIconId = R.drawable.clear_day;
        if (iconString.equals("clear-day")) {
            mIconId = R.drawable.clear_day;

        } else if (iconString.equals("clear-night")) {
            mIconId = R.drawable.clear_night;

        } else if (iconString.equals("rain")) {
            mIconId = R.drawable.rain;

        } else if (iconString.equals("snow")) {
            mIconId = R.drawable.snow;

        } else if (iconString.equals("sleet")) {
            mIconId = R.drawable.sleet;

        } else if (iconString.equals("wind")) {
            mIconId = R.drawable.wind;

        } else if (iconString.equals("fog")) {
            mIconId = R.drawable.fog;

        } else if (iconString.equals("cloudy")) {
            mIconId = R.drawable.cloudy;

        } else if (iconString.equals("partly-cloudy-day")) {
            mIconId = R.drawable.partly_cloudy;

        } else if (iconString.equals("partly-cloudy-night")) {
            mIconId = R.drawable.cloudy_night;

        }

        return mIconId;

    }

}
