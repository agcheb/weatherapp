package com.agcheb.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WeatherInCityActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "weatherincity";
    public static final String EXTRA_CITY = "city";


    TextView cityview;
    TextView weatherincity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_in_city);
        Intent intent = getIntent();

        if(intent != null){
            String city = intent.getStringExtra(EXTRA_CITY);
            String weather = intent.getStringExtra(EXTRA_MESSAGE);

            cityview = (TextView) findViewById(R.id.textview_city);
            weatherincity = (TextView) findViewById(R.id.textview_weather);

            cityview.setText(city);
            weatherincity.setText(weather);
        }
        Button btnShareWithFriends = (Button)findViewById(R.id.button_share);
        btnShareWithFriends.setOnClickListener(onClickListener);
    }



// Вопрос: при таком интенте В списке возможных приложений выдается много лишних(копировать, еще какие-то)
    // можно ли как-то это ограничить ? т.е я, например, хочу чтоб выбор был только среди смс приложения, и почты, и ватсап. но остальные вообще не рассматривались даже
    // возможно ли это?
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
                Intent intentBack = new Intent();
                intentBack.putExtra(MainScreenActivity.CALLBACKMSG, msgForSharing);
                setResult(RESULT_OK,intentBack);

                Intent intentShare = new Intent(Intent.ACTION_SEND);
                intentShare.setType("text/plain");
                intentShare.putExtra(Intent.EXTRA_TEXT,msgForSharing);

                startActivity(intentShare);
            }
        }
    };

}
