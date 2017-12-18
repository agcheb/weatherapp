package com.agcheb.weatherapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "weather.db"; // название бд
    public static final int DATABASE_VERSION = 1; // версия базы данных
    static final String TABLE_WEATHER = "weather"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_TEMP = "temp";
    public static final String COLUMN_DATE = "dt";
    public static final String COLUMN_PRESSURE = "note";
    public static final String COLUMN_HUMIDITY = "humidity";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_WEATHER + " (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CITY + " TEXT,"
                + COLUMN_TEMP + " REAL,"
                + COLUMN_DATE + " INTEGER,"
                + COLUMN_PRESSURE + " TEXT,"
                + COLUMN_HUMIDITY + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if ((oldVersion == 1) && (newVersion == 2)) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEATHER);
            onCreate(db);
        }
    }
}
