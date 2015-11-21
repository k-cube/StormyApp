package com.keivannorouzi.stormy.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.keivannorouzi.stormy.R;
import com.keivannorouzi.stormy.adapters.HourAdapter;
import com.keivannorouzi.stormy.weather.Hourly;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HourlyForecastActivity extends ActionBarActivity {

    private Hourly[] mHourly ;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_forecast);
        ButterKnife.bind(this);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Parcelable[] parcelable = intent.getParcelableArrayExtra(getString(R.string.HourlyForecast));
        mHourly = Arrays.copyOf(parcelable , parcelable.length , Hourly[].class);
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        HourAdapter adapter = new HourAdapter(this, mHourly);
        mRecyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

    }

}
