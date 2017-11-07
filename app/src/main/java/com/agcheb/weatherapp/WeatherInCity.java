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
    static String getPressure(Context context, int position){
        String[] weather = context.getResources().getStringArray(R.array.pressure);
        return weather[position];
    }
    static String getTommorowWeather(Context context, int position){
        String[] weather = context.getResources().getStringArray(R.array.w_tommorow);
        return weather[position];
    }
    static String getWeeklyWeather(Context context, int position){
        String[] weather = context.getResources().getStringArray(R.array.we_weekly);
        return weather[position];
    }
}
