package com.android.movies.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favorite_movie", indices = { @Index(value = "movieId") })
public class FavoriteMovie {

    @PrimaryKey
    private long movieId;
    private String title;
    private String posterUrl;

    public FavoriteMovie(long movieId, String title, String posterUrl) {
        this.movieId = movieId;
        this.title = title;
        this.posterUrl = posterUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public FavoriteMovie(Movie movie) {
        this(movie.getId(), movie.getOriginalTitle(), movie.getMoviePosterImageUrl());
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

}