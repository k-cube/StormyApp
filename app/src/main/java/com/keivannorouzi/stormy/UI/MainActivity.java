package com.keivannorouzi.stormy.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.keivannorouzi.stormy.R;
import com.keivannorouzi.stormy.weather.Current;
import com.keivannorouzi.stormy.weather.Daily;
import com.keivannorouzi.stormy.weather.Forecast;
import com.keivannorouzi.stormy.weather.Hourly;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final Context context = MainActivity.context ;
    private GoogleApiClient mGoogleApiClient ;

    private  Forecast mForecast;



    @Bind(R.id.timeLabel) TextView mTimeLabel;
    @Bind(R.id.locationLabel) TextView mLocationLabel;
    @Bind(R.id.tempretureLabel) TextView mTempretureLabel;
    @Bind(R.id.iconImageView) ImageView mIconImageView;
    @Bind(R.id.himidityValue) TextView mHimidityValue;
    @Bind(R.id.precipValue) TextView mPrecipValue;
    @Bind(R.id.summaryLabel) TextView mSummaryLabel ;
    @Bind(R.id.RefreshImageView) ImageView mRefreshImageView;
    @Bind(R.id.progressBar) ProgressBar mProgressBar;
    @Bind(R.id.DegreeType) TextView mDegreeType;
    @Bind(R.id.dailyButton) Button mDailyButton;
    private Location mLastLocation;
    private double mLatitude;
    private double mLongitude;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ButterKnife.bind(this);
//        getActionBar().hide();
        mProgressBar.setVisibility(View.INVISIBLE);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
//        final double langitude = -79.3941133 ;
//        final double latitude =  43.730804 ;



        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForecast(mLatitude, mLongitude);
            }
        });


        getForecast(mLatitude, mLongitude);




    }
    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    private void getForecast(double latitude, double langitude) {

        adjustType();

        String apiKey = "abaa9b12255d3e6bc8d4140fafbdccec";

        String forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                                        "/" + latitude + "," + langitude;


        if(isNetworkAvailable()) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    toggleRefresh();
                }
            });
//        implementing the request
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(forecastUrl)
                    .build();
//          Asyncronous Implementation
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {
//            Response response = call.execute(); This does not work since it clogs up the main thread, we need a asyncronous approach

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                toggleRefresh();
                            }
                        });

                        if (response.isSuccessful()) {
                            String JsonResponse = response.body().string();
                            mForecast = parseForecastDetailes(JsonResponse);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });

                        } else
                            alertUserAboutError();
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caouth: ", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "JSONException", e);
                    }
                }
            });
        }
        else {
            Toast.makeText(this, getString(R.string.Network_unavailable), Toast.LENGTH_LONG).show();
        }
    }


    private void toggleRefresh(){

        if(mProgressBar.getVisibility()==View.INVISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshImageView.setVisibility(View.INVISIBLE);
        }
        else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshImageView.setVisibility(View.VISIBLE);
        }
    }




    private void updateDisplay() {

        mDegreeType.setText("F"+(char) 0x00B0 );
        mTempretureLabel.setText(mForecast.getCurrent().getTemperature() + "");
        mTimeLabel.setText(mForecast.getCurrent().getFormattedTime() + "");
        mHimidityValue.setText(mForecast.getCurrent().getHumidity() + "");
        mPrecipValue.setText(mForecast.getCurrent().getPrecipChance() + "%");
        String timeZone = mForecast.getCurrent().getTimeZone();
        mLocationLabel.setText(timeZone.substring(timeZone.indexOf("/")+1));
        mSummaryLabel.setText(mForecast.getCurrent().getSummary());

        Drawable drawable = getResources().getDrawable(mForecast.getCurrent().getIconId());
        mIconImageView.setImageDrawable(drawable);
    }

    private Forecast parseForecastDetailes (String JsonData) throws JSONException{
        Forecast mForecast = new Forecast();
        mForecast.setCurrent(getCurrentForecast(JsonData));
        mForecast.setHourlies(getHourlyForecast(JsonData));
        mForecast.setDailies(getDailyForecast(JsonData));


        return mForecast;
    }

    private Daily[] getDailyForecast(String jsonData) throws JSONException{
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        JSONObject daily = forecast.getJSONObject("daily");
        JSONArray data = daily.getJSONArray("data");
        Daily[] dailyForecast = new Daily[data.length()];

        for (int i=0 ; i < data.length() ; i++){

            JSONObject jsonDay = data.getJSONObject(i);
            Daily day = new Daily();

            day.setIcon(jsonDay.getString("icon"));
            day.setTime(jsonDay.getLong("time"));
            day.setSummary(jsonDay.getString("summary"));
            day.setTemperatureMax(jsonDay.getDouble("temperatureMax"));
            day.setTimezone(timezone);

            dailyForecast[i] = day;
        }

        return dailyForecast;
    }

    private Hourly[] getHourlyForecast(String jsonData) throws JSONException{

        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        JSONObject hourly = forecast.getJSONObject("hourly");
        JSONArray  data = hourly.getJSONArray("data");
        Hourly[] hourlyForecast = new Hourly[data.length()];

        for (int i=0 ; i < data.length() ; i++){
            JSONObject jsonHour = data.getJSONObject(i);
            Hourly hour = new Hourly();

            hour.setTimezone(timezone);
            hour.setSummary(jsonHour.getString("summary"));
            hour.setTime(jsonHour.getLong("time"));
            hour.setTemperature(jsonHour.getDouble("temperature"));
            hour.setIcon(jsonHour.getString("icon"));

            hourlyForecast[i] = hour ;
        }

        return hourlyForecast;

    }

    private Current getCurrentForecast(String jsonResponse) throws JSONException{

        JSONObject forecast = new JSONObject(jsonResponse);
        String timezone = forecast.getString("timezone");
        JSONObject current = forecast.getJSONObject("currently");
        Current currentWeather = new Current();
        currentWeather.setHumidity(current.getDouble("humidity"));
        currentWeather.setPrecipChance(current.getDouble("precipProbability"));
        currentWeather.setTime(current.getLong("time"));
        currentWeather.setIcon(current.getString("icon"));
        currentWeather.setSummary(current.getString("summary"));
        currentWeather.setTemperatureF(current.getDouble("temperature"));
        currentWeather.setTimeZone(timezone);
        Log.d(TAG, "The time is: " + currentWeather.getFormattedTime());
        //String date = currentWeather.getFormattedTime().substring(0,10);
        //String time = currentWeather.getFormattedTime().substring(11);
        return currentWeather;
    }

    private boolean isNetworkAvailable() {

        boolean isAvailable = false;
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnected())
            isAvailable = true;
        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(),"Error dialog");
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitude = mLastLocation.getLatitude();
            mLongitude = mLastLocation.getLongitude();
            Log.d(TAG, "Location is" + " " + mLatitude + " / " + mLongitude );
        }
        else Log.d(TAG , "Current Location is NULL!!!!!!");

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG , "Connection SUSPENDED!!!!!!!!!!!");
        mLatitude = 37.8267;
        mLongitude = -122.423;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Connection Failed!!!!!!!!!!!");
    }

    private void adjustType() {
        mDegreeType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTempretureLabel.setText(mForecast.getCurrent().getTemperatureF() + "");
                mDegreeType.setText("C" + (char) 0x00B0);
                mDegreeType.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getForecast(mLatitude, mLongitude);
                    }
                });

            }
        });
    }
    @OnClick(R.id.dailyButton)
    public void startDailyActivity(View view){
        Intent intent = new Intent(this, DailyForecastActivity.class);
        intent.putExtra(getString(R.string.DailyForecastKey), mForecast.getDailies());
        intent.putExtra(getString(R.string.CURRENT_LOCATION),mForecast.getCurrent().getTimeZone());
        startActivity(intent);
    }

    @OnClick(R.id.hourlyButton)
    public void startHourlyActivity(View view){
        Intent intent = new Intent (this, HourlyForecastActivity.class);
        intent.putExtra(getString(R.string.HourlyForecast), mForecast.getHourlies());
        startActivity(intent);
    }

}
