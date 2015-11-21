package com.keivannorouzi.stormy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.keivannorouzi.stormy.R;
import com.keivannorouzi.stormy.weather.Hourly;

/**
 * Created by keivannorouzi on 2015-11-12.
 */
public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourViewHolder> {

    private Hourly[] mHours;
    private Context mContext ;

    public HourAdapter(Context context , Hourly[] hourly){
        mContext = context;
        mHours = hourly;
    }

    @Override
    public HourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_list_item,parent,false);//false is for whether attached to root!
        HourViewHolder viewHolder = new HourViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HourViewHolder holder, int position) {
        holder.bindHour(mHours[position]);
    }

    @Override
    public int getItemCount() {
        return mHours.length ;
    }

    public class HourViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        public TextView mTimeLabel ;
        public ImageView mIconImageView ;
        public TextView mTemperatureLabel;
        public TextView mSummaryLabel ;


        public HourViewHolder(View itemView) {
            super(itemView);

            mTimeLabel = (TextView) itemView.findViewById(R.id.timeLabel);
            mIconImageView = (ImageView) itemView.findViewById(R.id.iconImageView);
            mTemperatureLabel = (TextView) itemView.findViewById(R.id.temperatureLabel);
            mSummaryLabel = (TextView) itemView.findViewById(R.id.summaryLabel);
            itemView.setOnClickListener(this);
        }

        public void bindHour(Hourly hourly){

            mTimeLabel.setText(hourly.getFormattedTime());
            mSummaryLabel.setText(hourly.getSummary());
            mTemperatureLabel.setText(String.valueOf(hourly.getTemperature())+(char) 0x00B0);
            mIconImageView.setImageResource(hourly.getIconId());


        }


        @Override
        public void onClick(View v) {
            String time = mTimeLabel.getText() + "";
            String temperature = mTemperatureLabel.getText() + "";
            String summary = mSummaryLabel.getText() + "";
            String msg = String.format("At %s it will be %s and %s", time , temperature , summary);

            Toast.makeText(mContext , msg , Toast.LENGTH_LONG).show();
        }
    }

}
