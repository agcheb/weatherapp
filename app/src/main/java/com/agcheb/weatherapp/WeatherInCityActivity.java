package com.agcheb.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WeatherInCityActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "weatherincity";
    public static final String EXTRA_CITY = "city";
    public static final String CHECKBOX_PRESSURE = "checkbox1";
    public static final String CHECKBOX_TOMMOROW = "checkbox2";
    public static final String CHECKBOX_WEEKLY = "checkbox3";

    private static final String TAG = "########weather";
    private static final String CITY = "city";
    private static final String WEATHER_EFFECT = "weather_effect";


    TextView cityview;
    TextView weatherincity;
    TextView pressure;
    TextView tommorowWeather;
    TextView weeklyWeather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String city;
        String weather;
        int citynum;

        if(savedInstanceState != null){
            city = savedInstanceState.getString(CITY);
            weather = savedInstanceState.getString(WEATHER_EFFECT);

            cityview = (TextView) findViewById(R.id.textview_city);
            weatherincity = (TextView) findViewById(R.id.textview_weather);

            cityview.setText(city);
            weatherincity.setText(weather);
        }
        setContentView(R.layout.activity_weather_in_city);
        Intent intent = getIntent();

        if(intent != null){
            city = intent.getStringExtra(EXTRA_CITY);
            citynum = intent.getIntExtra(EXTRA_MESSAGE,0);
            boolean checkboxPressure = intent.getBooleanExtra(CHECKBOX_PRESSURE,false);
            boolean checkboxTommorow = intent.getBooleanExtra(CHECKBOX_TOMMOROW,false);
            boolean checkboxWeekly = intent.getBooleanExtra(CHECKBOX_WEEKLY,false);
            cityview = (TextView) findViewById(R.id.textview_city);
            weatherincity = (TextView) findViewById(R.id.textview_weather);
            pressure = (TextView) findViewById(R.id.text_pressure);
            tommorowWeather = (TextView) findViewById(R.id.text_tommorow_weather);
            weeklyWeather = (TextView) findViewById(R.id.text_weekly_weather);


            cityview.setText(city);
            weather = WeatherInCity.getWeather(WeatherInCityActivity.this,citynum);
            if(checkboxPressure){
                pressure.setText(WeatherInCity.getPressure(WeatherInCityActivity.this,citynum));
                pressure.setVisibility(View.VISIBLE);
            }
            if(checkboxTommorow){
                tommorowWeather.setText(WeatherInCity.getTommorowWeather(WeatherInCityActivity.this,citynum));
                tommorowWeather.setVisibility(View.VISIBLE);
            }
            if(checkboxWeekly){
                weeklyWeather.setText(WeatherInCity.getWeeklyWeather(WeatherInCityActivity.this,citynum));
                weeklyWeather.setVisibility(View.VISIBLE);
            }
            weatherincity.setText(weather);

        }

        Button btnShareWithFriends = (Button)findViewById(R.id.button_share);
        btnShareWithFriends.setOnClickListener(onClickListener);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(CITY,cityview.getText().toString());
        outState.putString(WEATHER_EFFECT,weatherincity.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        Log.d(TAG,"onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG,"onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG,"onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG,"onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG,"onDestroy");
        super.onDestroy();
    }

    // Вопрос: при таком интенте В списке возможных приложений выдается много лишних(копировать, еще какие-то)
    // можно ли как-то это ограничить ? т.е я, например, хочу чтоб выбор был только среди смс приложения, и почты, и ватсап. но остальные вообще не рассматривались даже
    // возможно ли это?
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
                Intent intentBack = new Intent();
                intentBack.putExtra(MainScreenActivity.CALLBACKMSG, msgForSharing);
                setResult(RESULT_OK,intentBack);

                Intent intentShare = new Intent(Intent.ACTION_SEND);
                intentShare.setType("text/plain");
                intentShare.putExtra(Intent.EXTRA_TEXT,msgForSharing);

                //соершит попытку отправки сообщения только если есть приложения подходящие по фильтру
                if(intentShare.resolveActivity(getPackageManager()) != null) {
                    startActivity(intentShare);
                }
            }
        }
    };

}
