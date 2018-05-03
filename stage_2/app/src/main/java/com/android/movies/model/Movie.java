package com.android.movies.model;

import java.util.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Movie {

    private static final String oldPattern = "yyyy-MM-dd";
    private static final String newPattern = "MM/dd/yyyy";

    private static final SimpleDateFormat oldDateFormat = new SimpleDateFormat(oldPattern);
    private static final SimpleDateFormat newDateFormat = new SimpleDateFormat(newPattern);

    private Long id;

    private Double averageVote;

    private String originalTitle;
    private String moviePosterImageUrl;
    private String plotSynopsis;
    private String releaseDate;

    public Movie(Long id, String originalTitle, String moviePosterImageUrl, String plotSynopsis, Double averageVote, String releaseDate) {

        this.id = id;
        this.originalTitle = originalTitle;
        this.moviePosterImageUrl = moviePosterImageUrl;
        this.plotSynopsis = plotSynopsis;
        this.averageVote = averageVote;
        this.releaseDate = releaseDate;

        if (this.releaseDate != null && (this.releaseDate = this.releaseDate.trim()).length() == 10) {

            try {
                Date date = oldDateFormat.parse(this.releaseDate);
                this.releaseDate = newDateFormat.format(date);
            } catch (ParseException pe) {
                pe.printStackTrace();
            }

        }

    }

    public Movie(FavoriteMovie favoriteMovie) {
        this(favoriteMovie.getMovieId(), favoriteMovie.getTitle(), favoriteMovie.getPosterUrl(), null, null, null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getMoviePosterImageUrl() {
        return moviePosterImageUrl;
    }

    public void setMoviePosterImageUrl(String moviePosterImageUrl) { this.moviePosterImageUrl = moviePosterImageUrl; }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }

    public void setAverageVote(Double averageVote) {
        this.averageVote = averageVote;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPlotSynopsis() { return plotSynopsis; }

    public Double getAverageVote() {
        return averageVote;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Movie Details:\n");
        stringBuilder.append("\tOriginal Title = " + originalTitle + "\n");
        stringBuilder.append("\tRelease Date = " + releaseDate + "\n");
        stringBuilder.append("\tMovie Poster Image URL = " + moviePosterImageUrl + "\n");
        stringBuilder.append("\tAverage Vote = " + averageVote + "\n");
        stringBuilder.append("\tPlot Synopsis = " + plotSynopsis);

        return stringBuilder.toString();

    }

}