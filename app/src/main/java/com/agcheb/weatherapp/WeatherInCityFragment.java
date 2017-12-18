package com.agcheb.weatherapp;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherInCityFragment extends Fragment {

    private static final String LOG_TAG = "!!!+++!!!";
    private final Handler handler = new Handler();

    WeatherDataSource weatherDataSource;

    private static final String DETAILS_FRAGMENT_TAG​ = "asasdasddasd234sdf";

    private int cityId;
    private String city = "";

    private boolean checkboxPressure;
    private boolean checkboxTommorow;
    private boolean checkboxWeekly;


    private TextView cityview;
    private TextView updatedTextView;
    private TextView weatherincity;

    public WeatherInCityFragment() {
        // Required empty public constructor
    }


    private void updateWeatherData(final String city){
        new Thread(){
            public void run(){
                final JSONObject json = WeatherDataLoader.getJSONData(getActivity().getApplicationContext(),city);

                if(json == null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(),getString(R.string.place_not_found),Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            renderWeather(json);
                        }
                    });
                }
            }
        }.start();
    }

    private void renderWeather(JSONObject json){
        try{

            String cityjson = json.getString("name").toUpperCase(Locale.US);
            cityview.setText(cityjson + ", " + json.getJSONObject("sys").getString("country"));

//            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            String pressurejson = main.getString("pressure");
            String humidityjson = main.getString("humidity");
//            detailsview.setText(details.getString("description").toUpperCase(Locale.US) + "\n" + "Humidity: " + main.getString("humidity") + "%" "\n" +
//                    "Pressure: " + main.getString("pressure") + "hPa");
            double tempjson = main.getDouble("temp");
            weatherincity.setText(String.format(Locale.US, "%.2f", tempjson) + " by Celcium");

            Log.d(LOG_TAG,"должна поменяться температура");
            DateFormat df = DateFormat.getDateInstance();
            long dtjson = json.getLong("dt");
            String updatedOn = df.format(new Date(dtjson * 1000));
            updatedTextView.setText("Last update: " + updatedOn);

            weatherDataSource.addNote(cityjson,tempjson,dtjson,pressurejson,humidityjson);
        }
        catch (Exception e){
            Log.d(LOG_TAG,"One or more fields not found in the JSON data");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_weather_in_city, container, false);

        String weather;
        cityview = (TextView) view.findViewById(R.id.textview_city);
        weatherincity = (TextView) view.findViewById(R.id.textview_weather);
        updatedTextView = (TextView) view.findViewById(R.id.updated_field);
        cityview.setText(WeatherInCity.getCity(getActivity())[cityId]);
        weather = WeatherInCity.getWeather(getActivity(),cityId);
        weatherDataSource = new WeatherDataSource(getActivity().getApplicationContext());
        weatherDataSource.open();

        WeatherInCity weatherInCity = WeatherInCity.weatherInCities[cityId];
        //weatherincity.setText(weather);
        updateWeatherData(WeatherInCity.getCity(getActivity())[cityId]);

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
