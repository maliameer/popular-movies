package com.android.movies.dao;

import java.util.List;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.OnConflictStrategy;

import com.android.movies.model.FavoriteMovie;

@Dao
public interface FavoriteMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addFavoriteMovie(FavoriteMovie favoriteMovie);

    @Query("SELECT DISTINCT * FROM favorite_movie WHERE movieId=:movieId")
    List<FavoriteMovie> findMovie(long movieId);

    @Query("SELECT DISTINCT * FROM favorite_movie")
    List<FavoriteMovie> findMovies();

    @Query("delete from favorite_movie where movieId=:movieId")
    void delete(long movieId);

}