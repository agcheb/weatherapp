package com.agcheb.weatherapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by agcheb on 29.10.17.
 */

public class MainScreenActivity extends Activity {
    public static final String APP_PREFERENCES = "mysettings";

    Spinner spinnerCities;
    TextView weatherText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenulayout);
        SharedPreferences sp = getSharedPreferences(APP_PREFERENCES,
                Context.MODE_PRIVATE);

        Button btnChooseWeather = (Button)findViewById(R.id.button_show_weather);
        spinnerCities = (Spinner)findViewById(R.id.spinner_for_cities);
        weatherText = (TextView)findViewById(R.id.textview_weather);

        loadPreferences();
        btnChooseWeather.setOnClickListener(onClickListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        savePreferences(APP_PREFERENCES,spinnerCities.getSelectedItemPosition());
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.button_show_weather){
                int cityNum = spinnerCities.getSelectedItemPosition();
                String city = (String)spinnerCities.getSelectedItem();
                String result = WeatherInCity.getWeather(MainScreenActivity.this,cityNum);
                Intent intent = new Intent(MainScreenActivity.this,WeatherInCityActivity.class);
                intent.putExtra(WeatherInCityActivity.EXTRA_CITY,city);
                intent.putExtra(WeatherInCityActivity.EXTRA_MESSAGE,result);

                startActivity(intent);
            }
        }
    };




    private void savePreferences(String key, int value) {
        SharedPreferences sharedPreferences = getSharedPreferences(
                APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    private void loadPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(
                APP_PREFERENCES, MODE_PRIVATE);
        int savedRadioIndex = sharedPreferences.getInt(
                APP_PREFERENCES, 0);
        spinnerCities.setSelection(savedRadioIndex);


    }
}
