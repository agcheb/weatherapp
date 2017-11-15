package com.agcheb.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by agcheb on 15.11.17.
 */

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_CITY_INDEX = "id";
    public static final String EXTRA_CH_BOX1 = "checkbox1";
    public static final String EXTRA_CH_BOX2 = "checkbox2";
    public static final String EXTRA_CH_BOX3 = "checkbox3";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        WeatherInCityFragment weatherInCityFragment = new WeatherInCityFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Intent intent = getIntent();
        int id = intent.getIntExtra(EXTRA_CITY_INDEX,0);
        boolean chbox1 = intent.getBooleanExtra(EXTRA_CH_BOX1,false);
        boolean chbox2 = intent.getBooleanExtra(EXTRA_CH_BOX2,false);
        boolean chbox3 = intent.getBooleanExtra(EXTRA_CH_BOX3,false);
        weatherInCityFragment.setCityId(id);
        weatherInCityFragment.setCheckboxPressure(chbox1);
        weatherInCityFragment.setCheckboxTommorow(chbox2);
        weatherInCityFragment.setCheckboxWeekly(chbox3);

        transaction.add(R.id.details_fragment,weatherInCityFragment);
        transaction.commit();

    }
}
