package com.agcheb.weatherapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by agcheb on 18.12.17.
 */

public class WeatherDataSource {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    private String[] weatherAllColumn = {
            DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_CITY,
            DatabaseHelper.COLUMN_TEMP,
            DatabaseHelper.COLUMN_DATE,
            DatabaseHelper.COLUMN_PRESSURE,
            DatabaseHelper.COLUMN_HUMIDITY
    };

    public WeatherDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public WeatherDBObject addNote(String city, double temp, long dt, String pressure, String humidity) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_CITY, city);
        values.put(DatabaseHelper.COLUMN_TEMP, temp);
        values.put(DatabaseHelper.COLUMN_DATE, dt);
        values.put(DatabaseHelper.COLUMN_PRESSURE, pressure);
        values.put(DatabaseHelper.COLUMN_HUMIDITY, humidity);
        long insertId = database.insert(DatabaseHelper.TABLE_WEATHER, null,
                values);
        WeatherDBObject newNote = new WeatherDBObject();
        newNote.setCity(city);
        newNote.setTemp(temp);
        newNote.setDt(dt);
        newNote.setPressure(pressure);
        newNote.setHumidity(humidity);
        newNote.setId(insertId);
        return newNote;
    }


    public void deleteWeatherDBO(String city) {
        database.delete(DatabaseHelper.TABLE_WEATHER, DatabaseHelper.COLUMN_CITY
                + " = " + city, null);
    }

    public void deleteAll() {
        database.delete(DatabaseHelper.TABLE_WEATHER, null, null);
    }

    public WeatherDBObject getAllNotes(String city) {

        Cursor cursor = database.query(DatabaseHelper.TABLE_WEATHER,
                weatherAllColumn, DatabaseHelper.COLUMN_CITY + "=" + city, null, null, null, null);

        cursor.moveToFirst();
            WeatherDBObject note = cursorToNote(cursor);
            // make sure to close the cursor
        cursor.close();
        return note;
    }

    private WeatherDBObject cursorToNote(Cursor cursor) {
        WeatherDBObject note = new WeatherDBObject();
        note.setId(cursor.getLong(0));
        note.setCity(cursor.getString(1));
        note.setTemp(cursor.getDouble(2));
        note.setDt(cursor.getLong(3));
        note.setPressure(cursor.getString(4));
        note.setHumidity(cursor.getString(5));
        return note;
    }

}
