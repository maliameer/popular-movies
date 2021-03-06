package com.android.movies.db;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavoriteMovieDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favoite_movie.db";
    private static final int DATABASE_VERSION = 1;

    public FavoriteMovieDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        FavoriteMovieTable.onCreate(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        FavoriteMovieTable.onUpgrade(database, oldVersion, newVersion);
    }

}