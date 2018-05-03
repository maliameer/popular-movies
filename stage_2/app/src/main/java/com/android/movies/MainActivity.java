package com.android.movies;

import java.util.LinkedList;

import java.io.IOException;
import java.util.List;

import android.os.Bundle;

import android.support.v4.app.LoaderManager;

import android.support.v4.content.Loader;
import android.support.v4.content.AsyncTaskLoader;

import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.movies.model.Movie;
import com.android.movies.model.FavoriteMovie;

import com.android.movies.service.MovieService;

import com.android.movies.utils.JsonUtils;

import com.android.movies.utils.RestUtils;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<LinkedList<Movie>> {

    public static final int MOVIES_LOADER_ID = 0;

    private Boolean loadFavoriteMovies = false;

    private Integer currentPageNumber = 1;
    private Integer totalNumberOfPages = 1;

    private String currentUrl = RestUtils.POPULAR_MOVIES_URL;

    private LinkedList<Movie> moviesInfo = new LinkedList<Movie>();

    private RecyclerView recyclerView;

    private MovieInfoRecyclerViewAdapter movieInfoRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RestUtils.setStrictMode();

        recyclerView = findViewById(R.id.movies_recycler_view);

        /*
            Keeping things simple here by using hard-coded Column Span Count. It is understood that it is not feasible for devices of different size.
            It can be dynamically managed by EITHER adding GlobalLayoutListener on RecyclerView's ViewTreeObserver OR customizing GridLayoutManager by extending it.
            But as said keeping this "Stage 1" implementation simple.
        */
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false));

        movieInfoRecyclerViewAdapter = new MovieInfoRecyclerViewAdapter(MainActivity.this, null);
        recyclerView.setAdapter(movieInfoRecyclerViewAdapter);

        getSupportLoaderManager().initLoader(MOVIES_LOADER_ID, null, this);

    }

    @Override
    public Loader<LinkedList<Movie>> onCreateLoader(int id, final Bundle loaderArgs) {

        return new AsyncTaskLoader<LinkedList<Movie>>(this) {

            @Override
            protected void onStartLoading() {

                if (moviesInfo != null && !moviesInfo.isEmpty()) {
                    deliverResult(moviesInfo);
                } else {
                    forceLoad();
                }

            }

            @Override
            public LinkedList<Movie> loadInBackground() {

                if (loadFavoriteMovies) {
                    System.out.println("Do load Favorite Movies. So, will be fetching Favorite Movies from the database to get the latest state in \"deliverResult()\" method.");
                    return null;
                } else {

                    String jsonPayload = "";
                    try {
                        jsonPayload = RestUtils.getJsonPayload(MainActivity.this, currentUrl);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }

                    if (!jsonPayload.isEmpty()) {

                        JSONObject jsonObject = null;
                        try {

                            jsonObject = new JSONObject(jsonPayload);
                            currentPageNumber = jsonObject.getInt("page");
                            totalNumberOfPages = jsonObject.getInt("total_pages");

                        } catch (JSONException je) {
                            je.printStackTrace();
                        }

                        return JsonUtils.parseMovies(jsonObject);

                    } else {
                        return null;
                    }

                }

            }

            public void deliverResult(LinkedList<Movie> movies) {

                if (loadFavoriteMovies) {
                    System.out.println("Do load Favorite Movies. So, fetching all Favorite Movies from the database to get latest state.");
                    moviesInfo = (new MovieService()).getFavoriteMovies(getApplicationContext());
                } else if (movies != null && !movies.isEmpty()) {
                    moviesInfo.addAll(movies);
                }

                super.deliverResult(moviesInfo);
                movieInfoRecyclerViewAdapter.setMovies(moviesInfo);

            }

        };

    }

    @Override
    public void onLoadFinished(Loader<LinkedList<Movie>> loader, LinkedList<Movie> movies) {
    }

    @Override
    public void onLoaderReset(Loader<LinkedList<Movie>> loader) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void invalidateData() {
        moviesInfo = new LinkedList<Movie>();
        movieInfoRecyclerViewAdapter.setMovies(null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        int selectedMenuItemId = menuItem.getItemId(), titleId = -1;
        boolean invalidateData = true;

        loadFavoriteMovies = false;
        if (selectedMenuItemId == R.id.favorite_movies) {

            loadFavoriteMovies = true;
            titleId = R.string.favorite_movies;

        } else if (selectedMenuItemId == R.id.load_more_movies) {

            invalidateData = false;
            if (currentPageNumber >= totalNumberOfPages) {
                return true;
            } else {

                this.currentUrl = RestUtils.getPagedUrl(this.currentUrl, ++currentPageNumber);
                System.out.println("New Page URL: " + this.currentUrl);

                if (currentPageNumber >= totalNumberOfPages) {
                    menuItem.setEnabled(false);//No more pages left, so disabling the Menu Item
                } else {
                    menuItem.setEnabled(true);
                }

            }

        } else {

            if (selectedMenuItemId == R.id.popular_movies) {
                titleId = R.string.app_name;
                this.currentUrl = RestUtils.POPULAR_MOVIES_URL;
            } else if (selectedMenuItemId == R.id.top_rated_movies) {
                titleId = R.string.top_rated_movies;
                this.currentUrl = RestUtils.TOP_RATED_MOVIES_URL;
            }

        }

        if (invalidateData) {

            if (titleId != -1) {
                setTitle(getString(titleId));
            }

            System.out.println("Selected Menu Item Id: " + selectedMenuItemId + "; Current URL: " + this.currentUrl);
            invalidateData();

        }

        getSupportLoaderManager().getLoader(MOVIES_LOADER_ID).forceLoad();

        return super.onOptionsItemSelected(menuItem);

    }

}