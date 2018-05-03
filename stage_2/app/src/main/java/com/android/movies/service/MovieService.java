package com.android.movies.service;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;

import com.android.movies.db.MovieDatabase;

import com.android.movies.model.FavoriteMovie;
import com.android.movies.model.Movie;

/*
  NOTE: Since, "Android Room" is used for Simple DAO operations as specified in specs for "Stage 2" i.e. just to add/remove Movies as Favorites and list Favorite Movies,
        instead of implementing ContentProvider implemented a simple Service class to perform actions like add/remove Movie as Favorite, find if Move is Favorite or not
        and list Favorite Movies
*/
public class MovieService {

    public boolean isFavoriteMovie(Context context, Long movieId) {
        List<FavoriteMovie> favoriteMovieList = MovieDatabase.getDatabase(context).favoriteMovieDao().findMovie(movieId);
        return (favoriteMovieList != null && !favoriteMovieList.isEmpty());
    }

    public void markMovieAsFavorite(Context context, FavoriteMovie favoriteMovie) {
        MovieDatabase.getDatabase(context).favoriteMovieDao().addFavoriteMovie(favoriteMovie);
    }

    public void removeMovieAsFavorite(Context context, Long movieId) {
        MovieDatabase.getDatabase(context).favoriteMovieDao().delete(movieId);
    }

    public LinkedList<Movie> getFavoriteMovies(Context context) {

        LinkedList<Movie> movieList = null;
        List<FavoriteMovie> favoriteMovieList = MovieDatabase.getDatabase(context).favoriteMovieDao().findMovies();
        if (favoriteMovieList != null && !favoriteMovieList.isEmpty()) {

            movieList = new LinkedList<Movie>();
            for (FavoriteMovie favoriteMovie : favoriteMovieList) {
                movieList.add(new Movie(favoriteMovie));
            }

        }

        return movieList;

    }

}