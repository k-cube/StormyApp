package com.keivannorouzi.stormy.weather;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by keivannorouzi on 15-11-01.
 */
public class Hourly implements Parcelable {
    private long mTime;
    private String mSummary;
    private double mTemperature;
    private String mIcon;
    private String mTimezone;

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public int getTemperature() {
        return (int) Math.round((mTemperature - 32) * 5/9);
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public String getIcon() {
        return mIcon;
    }

    public int getIconId(){
        return Forecast.getIconId(mIcon);
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getTimezone() {
        return mTimezone;
    }

    public void setTimezone(String timezone) {
        mTimezone = timezone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(mTime);
        dest.writeString(mSummary);
        dest.writeDouble(mTemperature);
        dest.writeString(mIcon);
        dest.writeString(mTimezone);

    }
    public Hourly(){}

    private Hourly(Parcel in){
        mTime  = in.readLong();
        mSummary = in.readString();
        mTemperature = in.readDouble();
        mIcon = in.readString();
        mTimezone = in.readString();

    }

    public static final Creator<Hourly> CREATOR = new Creator<Hourly>() {
        @Override
        public Hourly createFromParcel(Parcel source) {
            return new Hourly(source);
        }

        @Override
        public Hourly[] newArray(int size) {
            return new Hourly[size];
        }
    };

    public String getFormattedTime(){

        SimpleDateFormat formatter = new SimpleDateFormat("h a");
        formatter.setTimeZone(TimeZone.getTimeZone(mTimezone));

        Date dateTime = new Date(mTime*1000);
        String hour  = formatter.format(dateTime) ;
        return hour;

    }






}
