package com.agcheb.weatherapp;

import android.content.Intent;
import android.widget.RemoteViewsService;



public class NotesWidgetService extends RemoteViewsService{
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new NotesWidgetFactory(this.getApplicationContext(), intent);
    }
}
