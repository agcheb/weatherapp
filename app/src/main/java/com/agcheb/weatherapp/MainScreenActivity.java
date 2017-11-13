package com.agcheb.weatherapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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
    private static final String APP_PREFERENCES1 = "checkbox1";
    private static final String APP_PREFERENCES2 = "checkbox2";
    private static final String APP_PREFERENCES3 = "checkbox3";


    private final static int VERTICAL = 1;


    Spinner spinnerCities;
    CheckBox checkBoxPressure;
    CheckBox checkBoxTommorow;
    CheckBox checkBoxWeekly;
    TextView resultTextFromNextScreen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenulayout);
//        SharedPreferences sp = getSharedPreferences(APP_PREFERENCES,
//                Context.MODE_PRIVATE);

        RecyclerView nailsCategoriesRecyclerView = (RecyclerView) findViewById(R.id.recycler_view); //Найдем наш RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this); //Создадим LinearLayoutManager
        layoutManager.setOrientation(VERTICAL);//Обозначим ориентацию
        nailsCategoriesRecyclerView.setLayoutManager(layoutManager);//Назначим нашему RecyclerView созданный ранее layoutManager
        nailsCategoriesRecyclerView.setAdapter(new MyAdapter());//Назначим нашему RecyclerView адаптер


//        Button btnChooseWeather = (Button) findViewById(R.id.button_show_weather);
//        Log.d(TAG, "onCreate");

        checkBoxPressure = (CheckBox) findViewById(R.id.checkboxpressure);
        checkBoxTommorow = (CheckBox) findViewById(R.id.checkboxtommorow);
        checkBoxWeekly = (CheckBox) findViewById(R.id.checkboxweekly);

//        spinnerCities = (Spinner) findViewById(R.id.spinner_for_cities);
        resultTextFromNextScreen = (TextView) findViewById(R.id.text_view_smth);
        Log.d(TAG, "loadpreferences");

//        loadPreferences();
//        btnChooseWeather.setOnClickListener(onClickListener);
//        checkBoxPressure.setOnClickListener(onClickListener);
//        checkBoxTommorow.setOnClickListener(onClickListener);
//        checkBoxWeekly.setOnClickListener(onClickListener);
        Log.d(TAG, "vsebtns");

    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
//        savePreferences(APP_PREFERENCES, spinnerCities.getSelectedItemPosition()
//                , checkBoxPressure.isChecked()
//                , checkBoxTommorow.isChecked()
//                , checkBoxWeekly.isChecked());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //outState.putString("weather_effect");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }


    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }


//
//    View.OnClickListener onClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            if(view.getId() == R.id.button_show_weather){
//                int cityNum = spinnerCities.getSelectedItemPosition();
//                String city = (String)spinnerCities.getSelectedItem();
//                String result = WeatherInCity.getWeather(MainScreenActivity.this,cityNum);
//                Intent intent = new Intent(MainScreenActivity.this,WeatherInCityActivity.class);
//                intent.putExtra(WeatherInCityActivity.EXTRA_CITY,city);
//                intent.putExtra(WeatherInCityActivity.EXTRA_MESSAGE,cityNum);
//                intent.putExtra(WeatherInCityActivity.CHECKBOX_PRESSURE,checkBoxPressure.isChecked());
//                intent.putExtra(WeatherInCityActivity.CHECKBOX_TOMMOROW,checkBoxTommorow.isChecked());
//                intent.putExtra(WeatherInCityActivity.CHECKBOX_WEEKLY,checkBoxWeekly.isChecked());
//
//                startActivityForResult(intent, REQCODE);
//            }
//        }
//    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null){
            resultTextFromNextScreen.setText("Мы поделились через смс следующим сообщением:\n " + data.getStringExtra(CALLBACKMSG));
        }
    }

    private void savePreferences(String key, int value, boolean checkbox1, boolean checkbox2, boolean checkbox3) {
        SharedPreferences sharedPreferences = getSharedPreferences(
                APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.putBoolean(APP_PREFERENCES1, checkbox1);
        editor.putBoolean(APP_PREFERENCES2, checkbox2);
        editor.putBoolean(APP_PREFERENCES3, checkbox3);
        editor.apply();
    }

    private void loadPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(
                APP_PREFERENCES, MODE_PRIVATE);
        int savedRadioIndex = sharedPreferences.getInt(
                APP_PREFERENCES, 0);

        boolean checkbox1 = sharedPreferences.getBoolean(APP_PREFERENCES1,false);
        boolean checkbox2 = sharedPreferences.getBoolean(APP_PREFERENCES2,false);
        boolean checkbox3 = sharedPreferences.getBoolean(APP_PREFERENCES3,false);

        spinnerCities.setSelection(savedRadioIndex);
        checkBoxPressure.setChecked(checkbox1);
        checkBoxTommorow.setChecked(checkbox2);
        checkBoxWeekly.setChecked(checkbox3);
    }

    private void showWeather(String city, int cityNum){
        Intent intent = new Intent(MainScreenActivity.this,WeatherInCityActivity.class);
        intent.putExtra(WeatherInCityActivity.EXTRA_CITY,city);
        intent.putExtra(WeatherInCityActivity.EXTRA_MESSAGE,cityNum);
        intent.putExtra(WeatherInCityActivity.CHECKBOX_PRESSURE,checkBoxPressure.isChecked());
        intent.putExtra(WeatherInCityActivity.CHECKBOX_TOMMOROW,checkBoxTommorow.isChecked());
        intent.putExtra(WeatherInCityActivity.CHECKBOX_WEEKLY,checkBoxWeekly.isChecked());

        startActivityForResult(intent, REQCODE);

    }


    private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private  TextView cityNameitem;

        public MyViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.city_list_item,parent,false));
            itemView.setOnClickListener(this);
            cityNameitem = (TextView)itemView.findViewById(R.id.city_name_textview);
        }

        void bind(int position) {
            String category = WeatherInCity.getCity(MainScreenActivity.this)[position];
            cityNameitem.setText(category);
        }
        @Override
        public void onClick(View view) {
            showWeather(this.cityNameitem.getText().toString(),this.getLayoutPosition());
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            return new MyViewHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return WeatherInCity.getCity(MainScreenActivity.this).length;
        }
    }
}
