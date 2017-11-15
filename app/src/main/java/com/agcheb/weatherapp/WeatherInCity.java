package com.agcheb.weatherapp;

import android.content.Context;

/**
 * Created by agcheb on 29.10.17.
 */

public class WeatherInCity {

    private int resorceId;

    private WeatherInCity(int resorceId) {
        this.resorceId = resorceId;
    }

    static final WeatherInCity[] weatherInCities = {
            new WeatherInCity(R.drawable.sun),
            new WeatherInCity(R.drawable.rain),
            new WeatherInCity(R.drawable.sun),
            new WeatherInCity(R.drawable.sun),
            new WeatherInCity(R.drawable.snow),
            new WeatherInCity(R.drawable.snow),
            new WeatherInCity(R.drawable.snow)
    };

    public int getResorceId() {
        return resorceId;
    }


    static String[] getCity(Context context){
        String[] city = context.getResources().getStringArray(R.array.cities);
        return city;
    }
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
