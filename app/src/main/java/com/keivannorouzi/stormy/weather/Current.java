package com.keivannorouzi.stormy.weather;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by keivannorouzi on 15-10-19.
 */
public class Current {
    private static final String TAG = Current.class.getSimpleName();

    private String mIcon ;
    private long mTime;
    private double mHumidity;
    private double mTemperatureF;
    private double mTemperatureC;
    private double mPrecipChance;
    private String mSummary;
    private String mTimeZone;

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String mIcon) {
        this.mIcon = mIcon;
    }

    public int getIconId(){

        return Forecast.getIconId(mIcon);
    }

    public long getTime() {
        return mTime;
    }

    public String getFormattedTime (){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        formatter.setTimeZone(TimeZone.getTimeZone(mTimeZone));

        Date dateTime = new Date(getTime()*1000);
        String timeString = formatter.format(dateTime);


        return timeString;

    }

    public void setTime(long mTime) {
        this.mTime = mTime;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double mHumidity) {
        this.mHumidity = mHumidity;
    }

    public int getTemperatureF() {
        return (int) Math.round(mTemperatureF);
    }


    public void setTemperatureC(double temperatureC) {
        mTemperatureC = temperatureC;
    }

    public int getTemperature() {
        mTemperatureC =  (mTemperatureF - 32) * 5/9;
        return (int) Math.round(mTemperatureC);
    }

    public void setTemperatureF(double mTemperature) {
        this.mTemperatureF = mTemperature;
    }

    public int getPrecipChance() {
        return (int) Math.round(mPrecipChance * 100);
    }

    public void setPrecipChance(double mPrecipChance) {
        this.mPrecipChance = mPrecipChance;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String mSummary) {
        this.mSummary = mSummary;
    }
}
