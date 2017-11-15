package com.agcheb.weatherapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherInCityFragment extends Fragment {


    private int cityId;
    private String city = "";

    private boolean checkboxPressure;
    private boolean checkboxTommorow;
    private boolean checkboxWeekly;





    TextView cityview;
    TextView weatherincity;
    TextView pressure;
    TextView tommorowWeather;
    TextView weeklyWeather;
    public WeatherInCityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


       View view = inflater.inflate(R.layout.fragment_weather_in_city, container, false);

        String weather;
        cityview = (TextView) view.findViewById(R.id.textview_city);
        weatherincity = (TextView) view.findViewById(R.id.textview_weather);
        pressure = (TextView) view.findViewById(R.id.text_pressure);
        tommorowWeather = (TextView) view.findViewById(R.id.text_tommorow_weather);
        weeklyWeather = (TextView) view.findViewById(R.id.text_weekly_weather);
        cityview.setText(WeatherInCity.getCity(getActivity())[cityId]);
        weather = WeatherInCity.getWeather(getActivity(),cityId);

        WeatherInCity weatherInCity = WeatherInCity.weatherInCities[cityId];
        if(checkboxPressure){
            pressure.setText(WeatherInCity.getPressure(getActivity(),cityId));
            pressure.setVisibility(View.VISIBLE);
        }
        if(checkboxTommorow){
            tommorowWeather.setText(WeatherInCity.getTommorowWeather(getActivity(),cityId));
            tommorowWeather.setVisibility(View.VISIBLE);
        }
        if(checkboxWeekly){
            weeklyWeather.setText(WeatherInCity.getWeeklyWeather(getActivity(),cityId));
            weeklyWeather.setVisibility(View.VISIBLE);
        }
        weatherincity.setText(weather);

        ImageView imageResourceId = (ImageView)view.findViewById(R.id.image_weather);
        imageResourceId.setImageResource(weatherInCity.getResorceId());
        Button btnShareWithFriends = (Button)view.findViewById(R.id.button_share);
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

                //соершит попытку отправки сообщения только если есть приложения подходящие по фильтру
                if(intentShare.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intentShare);
                }
            }
        }
    };


    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCheckboxPressure(boolean checkboxPressure) {
        this.checkboxPressure = checkboxPressure;
    }

    public void setCheckboxTommorow(boolean checkboxTommorow) {
        this.checkboxTommorow = checkboxTommorow;
    }

    public void setCheckboxWeekly(boolean checkboxWeekly) {
        this.checkboxWeekly = checkboxWeekly;
    }
}
