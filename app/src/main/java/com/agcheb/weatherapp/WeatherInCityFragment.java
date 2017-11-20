package com.agcheb.weatherapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

    private static final String DETAILS_FRAGMENT_TAG​ = "asasdasddasd234sdf";

    private int cityId;
    private String city = "";

    private boolean checkboxPressure;
    private boolean checkboxTommorow;
    private boolean checkboxWeekly;


    private TextView cityview;
    private TextView weatherincity;

    public WeatherInCityFragment() {
        // Required empty public constructor
    }

//    public static WeatherInCityFragment init(Bundle bundle){
//        WeatherInCityFragment fragment = new WeatherInCityFragment();
//        fragment.setArguments(bundle);
//        return fragment;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


       View view = inflater.inflate(R.layout.fragment_weather_in_city, container, false);

        String weather;
        cityview = (TextView) view.findViewById(R.id.textview_city);
        weatherincity = (TextView) view.findViewById(R.id.textview_weather);
        cityview.setText(WeatherInCity.getCity(getActivity())[cityId]);
        weather = WeatherInCity.getWeather(getActivity(),cityId);

        WeatherInCity weatherInCity = WeatherInCity.weatherInCities[cityId];
        weatherincity.setText(weather);

        ImageView imageResourceId = (ImageView)view.findViewById(R.id.image_weather);
        imageResourceId.setImageResource(weatherInCity.getResorceId());

        //Создаем Вложенный Фрагмент
        Bundle bundle = new Bundle();
        bundle.putInt(DetailedInfoFragment.EXTRA_CITY_INDEX,cityId);
        bundle.putBoolean(DetailedInfoFragment.EXTRA_CH_BOX1,checkboxPressure);
        bundle.putBoolean(DetailedInfoFragment.EXTRA_CH_BOX2,checkboxTommorow);
        bundle.putBoolean(DetailedInfoFragment.EXTRA_CH_BOX3,checkboxWeekly);

        FragmentManager fragmentManager = getChildFragmentManager();
        DetailedInfoFragment detailedInfoFragment = (DetailedInfoFragment)fragmentManager.findFragmentByTag(DETAILS_FRAGMENT_TAG​);
        if(detailedInfoFragment == null){
            FragmentTransaction fragmentTransaction =
                    fragmentManager.beginTransaction();
            detailedInfoFragment = DetailedInfoFragment.init(bundle);
            fragmentTransaction.replace(R.id.details_info_container,detailedInfoFragment,DETAILS_FRAGMENT_TAG​);
            fragmentTransaction.commit();
        }

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

                if(intentShare.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intentShare);
                }
            }
        }
    };


    public void setCityId(int cityId) {
        this.cityId = cityId;
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
