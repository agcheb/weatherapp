package com.agcheb.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agcheb on 27.12.17.
 */

public class NotesWidgetFactory implements RemoteViewsService.RemoteViewsFactory {
    Context wContext;
    private WeatherDBObject records;

    public NotesWidgetFactory(Context applicationContext, Intent intent) {
        wContext = applicationContext;
    }

    @Override
    public void onCreate() {
        records = null;
    }

    @Override
    public void onDataSetChanged() {
        records=null;
        WeatherDataSource cityDataSource = new WeatherDataSource(wContext);
        cityDataSource.open();
        records = cityDataSource.getAllNotes();
        cityDataSource.close();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews rView = new RemoteViews(wContext.getPackageName(),R.layout.weather_widget);
        rView.setTextViewText(R.id.appwidget_text,records.toString());

        Intent clickIntent = new Intent();
        clickIntent.putExtra(WeatherWidget.NOTE_TEXT, records.toString());
        rView.setOnClickFillInIntent(R.id.appwidget_text,clickIntent);
        return rView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
