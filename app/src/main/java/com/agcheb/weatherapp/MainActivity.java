package com.agcheb.weatherapp;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements MainMenuFragment.CitiesListListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         }

    @Override
    public void onListItemClick(int id, boolean chbox1, boolean chbox2) {

        View fragmentContainer = findViewById(R.id.fragment_container);

        if(fragmentContainer != null) {

            WeatherInCityFragment weatherInCityFragment = new WeatherInCityFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            weatherInCityFragment.setCityId(id);
            weatherInCityFragment.setCheckboxPressure(chbox1);
            weatherInCityFragment.setCheckboxHumidity(chbox2);

            transaction.replace(R.id.fragment_container, weatherInCityFragment);

            transaction.commit();
        }
        else{
            Intent intent = new Intent(this,DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_CITY_INDEX,id);
            intent.putExtra(DetailActivity.EXTRA_CH_BOX1,chbox1);
            intent.putExtra(DetailActivity.EXTRA_CH_BOX2,chbox2);
            startActivity(intent);
        }
    }
}
