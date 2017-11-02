package com.agcheb.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class WeatherInCityActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "weatherincity";
    public static final String EXTRA_CITY = "city";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_in_city);
        Intent intent = getIntent();

        if(intent != null){
            String city = intent.getStringExtra(EXTRA_CITY);
            String weather = intent.getStringExtra(EXTRA_MESSAGE);

            TextView cityview = (TextView) findViewById(R.id.textview_city);
            TextView weatherincity = (TextView) findViewById(R.id.textview_weather);

            cityview.setText(city);
            weatherincity.setText(weather);
        }
    }
}
