package com.agcheb.weatherapp;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherInCityFragment extends Fragment {


    private static final String LOG_TAG = "!!!+++!!!";
    private final Handler handler = new Handler();

    WeatherDataSource weatherDataSource;

    private static final String DETAILS_FRAGMENT_TAG​ = "asasdasddasd234sdf";

    private int cityId;
    private String city = "";

    private boolean checkboxPressure;
    private boolean checkboxHumidity;


    private TextView cityview;
    private TextView updatedTextView;
    private TextView weatherincity;
    private TextView pressureview;
    private TextView humidityView;

    public WeatherInCityFragment() {
        // Required empty public constructor
    }


    private void updateWeatherData(final String city){
        new Thread(){
            public void run(){
                final JSONObject json = WeatherDataLoader.getJSONData(getActivity().getApplicationContext(),city);

                if(json == null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(),getString(R.string.place_not_found),Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            renderWeather(json);
                        }
                    });
                }
            }
        }.start();
    }

    private void renderWeather(JSONObject json){
        try{

            String cityjson = json.getString("name").toUpperCase(Locale.US);
            cityview.setText(cityjson + ", " + json.getJSONObject("sys").getString("country"));

            JSONObject main = json.getJSONObject("main");
            String pressurejson = main.getString("pressure");
            String humidityjson = main.getString("humidity");
            if (checkboxPressure){
                pressureview.setText("Pressure: " + main.getString("pressure") + "hPa");
                pressureview.setVisibility(View.VISIBLE);
            }
            if (checkboxHumidity){
                humidityView.setText("Humidity: " + main.getString("humidity") + "%");
                humidityView.setVisibility(View.VISIBLE);
            }
            double tempjson = main.getDouble("temp");
            weatherincity.setText(String.format(Locale.US, "%.2f", tempjson) + " by Celcium");

            Log.d(LOG_TAG,"должна поменяться температура");
            DateFormat df = DateFormat.getDateInstance();
            long dtjson = json.getLong("dt");
            String updatedOn = df.format(new Date(dtjson * 1000));
            updatedTextView.setText("Last update: " + updatedOn);

            weatherDataSource.addNote(cityjson,tempjson,dtjson,pressurejson,humidityjson);
        }
        catch (Exception e){
            Log.d(LOG_TAG,"One or more fields not found in the JSON data");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_weather_in_city, container, false);

        cityview = (TextView) view.findViewById(R.id.textview_city);
        weatherincity = (TextView) view.findViewById(R.id.textview_weather);
        updatedTextView = (TextView) view.findViewById(R.id.updated_field);
        cityview.setText(WeatherInCity.getCity(getActivity())[cityId]);
        pressureview = (TextView) view.findViewById(R.id.text_pressure);
        humidityView = (TextView) view.findViewById(R.id.text_tommorow_weather);


        weatherDataSource = new WeatherDataSource(getActivity().getApplicationContext());
        weatherDataSource.open();

        updateWeatherData(WeatherInCity.getCity(getActivity())[cityId]);


        FloatingActionButton btnShareWithFriends = (FloatingActionButton) view.findViewById(R.id.button_share);
        btnShareWithFriends.setOnClickListener(onClickListener);


        return view;
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.button_share){
                StringBuilder messageBuilder = new StringBuilder();
                String city = cityview.getText().toString();
                String weather = weatherincity.getText().toString();
                String msgForSharing = messageBuilder.append(city)
                        .append(" - ")
                        .append(weather).toString();

                Intent intentShare = new Intent(Intent.ACTION_SEND);
                intentShare.setType("text/plain");
                intentShare.putExtra(Intent.EXTRA_TEXT,msgForSharing);

                if(intentShare.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intentShare);
                }
            }
        }
    };


    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public void setCheckboxPressure(boolean checkboxPressure) {
        this.checkboxPressure = checkboxPressure;
    }

    public void setCheckboxHumidity(boolean checkboxHumidity) {
        this.checkboxHumidity = checkboxHumidity;
    }

    }
