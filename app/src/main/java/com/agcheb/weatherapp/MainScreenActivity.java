package com.agcheb.weatherapp;

import android.app.Activity;
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
    Spinner spinnerCities;
    TextView weatherText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenulayout);
        Button btnChooseWeather = (Button)findViewById(R.id.button_show_weather);
        spinnerCities = (Spinner)findViewById(R.id.spinner_for_cities);
        btnChooseWeather.setOnClickListener(onClickListener);
        weatherText = (TextView)findViewById(R.id.textview_weather);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.button_show_weather){
                String result = WeatherInCity.getWeather(MainScreenActivity.this,spinnerCities.getSelectedItemPosition());
                weatherText.setText(result);
            }
        }
    };
}
