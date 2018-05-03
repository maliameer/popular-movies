package com.android.movies.db;

import android.content.Context;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.android.movies.model.FavoriteMovie;

import com.android.movies.dao.FavoriteMovieDao;

@Database(entities = {FavoriteMovie.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    private static MovieDatabase movieDatabaseInstance;

    public abstract FavoriteMovieDao favoriteMovieDao();

    public static MovieDatabase getDatabase(Context context) {

        if (movieDatabaseInstance == null) {
            movieDatabaseInstance = //Room.databaseBuilder(context, MovieDatabase.class, "movie_database")
                                    Room.inMemoryDatabaseBuilder(context.getApplicationContext(), MovieDatabase.class)
                                    // To simplify the exercise, allow queries on the main thread.
                                    // Don't do this on a real app!
                                    .allowMainThreadQueries()
                                    // recreate the database if necessary
                                    .fallbackToDestructiveMigration()
                                    .build();
        }
        return movieDatabaseInstance;

    }

    public static void destroyInstance() {
        movieDatabaseInstance = null;
    }

}