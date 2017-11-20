package com.agcheb.weatherapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailedInfoFragment extends Fragment {

    public static final String EXTRA_BUNDLE = "key";

    public static final String EXTRA_CITY_INDEX = "id";
    public static final String EXTRA_CH_BOX1 = "checkbox1";
    public static final String EXTRA_CH_BOX2 = "checkbox2";
    public static final String EXTRA_CH_BOX3 = "checkbox3";




    private TextView pressure;
    private TextView tommorowWeather;
    private TextView weeklyWeather;


    public static DetailedInfoFragment init(Bundle bundle){
        DetailedInfoFragment fragment = new DetailedInfoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    public DetailedInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detailed_info, container, false);


        pressure = (TextView) view.findViewById(R.id.text_pressure);
        tommorowWeather = (TextView) view.findViewById(R.id.text_tommorow_weather);
        weeklyWeather = (TextView) view.findViewById(R.id.text_weekly_weather);

        Bundle argschbox = getArguments();

        int cityId = argschbox.getInt(EXTRA_CITY_INDEX);
        if(argschbox.getBoolean(EXTRA_CH_BOX1)){
            pressure.setText(WeatherInCity.getPressure(getActivity(),cityId));
            pressure.setVisibility(View.VISIBLE);
        }
        if(argschbox.getBoolean(EXTRA_CH_BOX2)){
            tommorowWeather.setText(WeatherInCity.getTommorowWeather(getActivity(),cityId));
            tommorowWeather.setVisibility(View.VISIBLE);
        }
        if(argschbox.getBoolean(EXTRA_CH_BOX3)){
            weeklyWeather.setText(WeatherInCity.getWeeklyWeather(getActivity(),cityId));
            weeklyWeather.setVisibility(View.VISIBLE);
        }

        return view;
    }

}
