package com.agcheb.weatherapp;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by agcheb on 27.12.17.
 */

class CityDataSource {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    private String[] weatherAllColumn = {
            DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_CITY
    };

    public CityDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }
}
