package com.agcheb.weatherapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class WeatherWidget extends AppWidgetProvider {

    public static final String UPDATE_WIDGET_ACTION = "android.appwidget.action.APPWIDGET_UPDATE";
    public static final String ITEM_ON_CLICK__ACTION = "android.appwidget.action.ITEM_ON_CLICK";
    public static final String NOTE_TEXT = "note_text";




     void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.weather_widget);
        //views.setTextViewText(R.id.appwidget_text, widgetText);
         setList(views,context,appWidgetId);
         setListClick(views,context);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    void setList(RemoteViews rv, Context context,int appWidgetId){
        Intent adapter = new Intent(context, NotesWidgetService.class);
        adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
        //rv.setRemoteAdapter(R.id.recycler_view, adapter);
        //rv.setEmptyView(R.id.recycler_view,R.id.empty_view);
    }
    void setListClick(RemoteViews rv, Context context){
        Intent listClickIntent = new Intent(context, WeatherWidget.class);
        listClickIntent.setAction(ITEM_ON_CLICK__ACTION);
        PendingIntent listClickPIntent = PendingIntent.getBroadcast(context, 0,listClickIntent,0);
        //rv.setPendingIntentTemplate(R.id.recycler_view,listClickPIntent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

