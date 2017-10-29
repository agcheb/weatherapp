package com.agcheb.weatherapp;

import android.content.Context;

/**
 * Created by agcheb on 29.10.17.
 */

public class WeatherInCity {
    static String getWeather(Context context, int position){
        String[] weather = context.getResources().getStringArray(R.array.weather);
        return weather[position];
    }
}
