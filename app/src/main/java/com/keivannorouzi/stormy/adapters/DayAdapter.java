package com.keivannorouzi.stormy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.keivannorouzi.stormy.R;
import com.keivannorouzi.stormy.weather.Daily;

/**
 * Created by keivannorouzi on 15-11-03.
 */
public class DayAdapter extends BaseAdapter {

    Context mContext;
    Daily[] mDaily;

    public DayAdapter(Context context, Daily[] daily){
        mContext = context ;
        mDaily = daily;
    }

    @Override
    public int getCount() {
        return mDaily.length;
    }

    @Override
    public Object getItem(int position) {
        return mDaily[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;// we aren't going to use this.
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView==null) {
            //brand new
            convertView = LayoutInflater.from(mContext).inflate(R.layout.daily_list_item, null);
            holder = new ViewHolder();
            holder.dayLabel = (TextView) convertView.findViewById(R.id.dayNameLabel);
            holder.iconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
            holder.temperatureLabel = (TextView) convertView.findViewById(R.id.temperatureLabel);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Daily daily = mDaily[position];

        holder.temperatureLabel.setText(daily.getTemperatureMax()+ "");
        if(position==0){
            holder.dayLabel.setText("Today");
        }else
            holder.dayLabel.setText(daily.getDayOfTheWeek());

        holder.iconImageView.setImageResource(daily.getIconId());

        return convertView;
    }


    public static class ViewHolder{

        ImageView iconImageView;
        TextView temperatureLabel ;
        TextView dayLabel;
    }
}
