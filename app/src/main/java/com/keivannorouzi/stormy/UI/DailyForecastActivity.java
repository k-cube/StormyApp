package com.keivannorouzi.stormy.UI;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.keivannorouzi.stormy.R;
import com.keivannorouzi.stormy.adapters.DayAdapter;
import com.keivannorouzi.stormy.weather.Daily;

import java.util.Arrays;

public class DailyForecastActivity extends ListActivity {
    public static final String TAG = DailyForecastActivity.class.getSimpleName();
    private Daily[] mDaily;
    private String mTimezone;

    TextView mLocationLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);
        mLocationLabel = (TextView) findViewById(R.id.LocationLabel);

//        String[] daysOfTheWeek = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,daysOfTheWeek);
//        setListAdapter(adapter);

        Intent intent = getIntent();
        mTimezone = intent.getStringExtra(getString(R.string.CURRENT_LOCATION));
        String city = mTimezone.substring(mTimezone.indexOf("/")+1);
        mLocationLabel.setText(city);
        Parcelable[] parcelables = intent.getParcelableArrayExtra(getString(R.string.DailyForecastKey));
        mDaily = Arrays.copyOf(parcelables, parcelables.length, Daily[].class);

        DayAdapter adapter = new DayAdapter(this, mDaily );
        setListAdapter(adapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String day = mDaily[position].getDayOfTheWeek();
        String condition = mDaily[position].getSummary();
        String highTemp = mDaily[position].getTemperatureMax()+"";
        String msg = String.format("On %s the high will be %s and it will be %s" ,
                day,
                highTemp,
                condition) ;
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
}
