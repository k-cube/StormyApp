package com.keivannorouzi.stormy.weather;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by keivannorouzi on 15-11-01.
 */

public class Daily implements Parcelable {
    private long mTime;
    private String mSummary;
    private double mTemperatureMax;
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

    public int getTemperatureMax() {

        return (int) Math.round((mTemperatureMax - 32) * 5/9 );
    }

    public void setTemperatureMax(double temperatureMax) {
        mTemperatureMax = temperatureMax;
    }

    public String getIcon() {
        return mIcon;
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

    public int getIconId(){
        return Forecast.getIconId(mIcon);
    }

    public String getDayOfTheWeek(){
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
        formatter.setTimeZone(TimeZone.getTimeZone(mTimezone));

        Date dateTime = new Date(getTime()*1000);
        String timeString = formatter.format(dateTime);
        return timeString;
    }

    //make this class parcelable

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(mSummary);
        dest.writeString(mIcon);
        dest.writeLong(mTime);
        dest.writeDouble(mTemperatureMax);
        dest.writeString(mTimezone);

    }

    public Daily(){
    }


    public Daily(Parcel in){

        mSummary = in.readString();
        mIcon = in.readString();
        mTime = in.readLong();
        mTemperatureMax = in.readDouble();
        mTimezone = in.readString();
    }

    public static final Creator<Daily> CREATOR = new Creator<Daily>() {
        @Override
        public Daily createFromParcel(Parcel source) {
            return new Daily(source);
        }

        @Override
        public Daily[] newArray(int size) {
            return new Daily[size];
        }
    };
}
