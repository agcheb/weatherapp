package com.agcheb.weatherapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by agcheb on 29.10.17.
 */

public class MainScreenActivity extends Activity {
    private static final String TAG = "########mainscreen";
    public static final String APP_PREFERENCES = "mysettings";
    public static final String CALLBACKMSG = "callbackmsg";

    private static final int REQCODE = 1;

    Spinner spinnerCities;
    TextView resultTextFromNextScreen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenulayout);
        SharedPreferences sp = getSharedPreferences(APP_PREFERENCES,
                Context.MODE_PRIVATE);

        Button btnChooseWeather = (Button)findViewById(R.id.button_show_weather);
        spinnerCities = (Spinner)findViewById(R.id.spinner_for_cities);
        resultTextFromNextScreen = (TextView)findViewById(R.id.text_view_smth);

        loadPreferences();
        btnChooseWeather.setOnClickListener(onClickListener);
    }

    @Override
    protected void onPause() {
        Log.d(TAG,"onPause");
        super.onPause();
        savePreferences(APP_PREFERENCES,spinnerCities.getSelectedItemPosition());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //outState.putString("weather_effect");
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
    protected void onStop() {
        Log.d(TAG,"onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG,"onDestroy");
        super.onDestroy();
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

                startActivityForResult(intent, REQCODE);
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null){
            resultTextFromNextScreen.setText("Мы поделились через смс следующим сообщением:\n " + data.getStringExtra(CALLBACKMSG));
        }
    }

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
