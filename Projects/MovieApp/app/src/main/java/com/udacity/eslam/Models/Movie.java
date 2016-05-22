package com.udacity.eslam.Models;

import com.udacity.eslam.Utility.Values;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Eslam on 5/22/2016.
 */

public class Movie {

    private Double _id;
    private String mTitle;
    private String mOverview;
    private Double mPopularity;
    private String posterPath;
    private String mReleaseDate;
    private Double mVoteAverage;
    private Double mVotCount;
    private Double mRuntime;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public Double get_id() {
        return _id;
    }

    public void set_id(Double _id) {
        this._id = _id;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        this.mOverview = overview;
    }

    public Double getPopularity() {
        return mPopularity;
    }

    public void setPopularity(Double populraity) {
        this.mPopularity = populraity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }


    public Double getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.mVoteAverage = voteAverage;
    }

    public Double getVotCount() {
        return mVotCount;
    }

    public void setVotCount(Double votCount) {
        this.mVotCount = votCount;
    }

    public Double getRuntime() {
        return mRuntime;
    }

    public void setRuntime(Double runtime) {
        this.mRuntime = runtime;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public static Movie getMovieFromJSONObject(JSONObject jMovie) throws JSONException {
        Movie movie = new Movie();
        movie.set_id(jMovie.getDouble(Values.KEY_ID));
        movie.setTitle(jMovie.getString(Values.KEY_TITLE));
        movie.setOverview(jMovie.getString(Values.KEY_OVERVIEW));
        movie.setPosterPath(jMovie.getString(Values.KEY_POSTER_PATH));
        movie.setReleaseDate(jMovie.getString(Values.KEY_RELEASE_DATE));
        movie.setPopularity(jMovie.getDouble(Values.KEY_POPULARITY));
        movie.setRuntime(jMovie.getDouble(Values.KEY_RUNTIME));
        movie.setVotCount(jMovie.getDouble(Values.KEY_VOTE_COUNT));
        movie.setVoteAverage(jMovie.getDouble(Values.KEY_VOTE_AVERAGE));

        return movie;
    }

    public static ArrayList<Movie> getMoviesListFromJSONArray(JSONArray jMoviesList) {
        ArrayList<Movie> moviesList = new ArrayList<>();
        for (int i = 0; i < jMoviesList.length(); i++) {
            try {
                moviesList.add(getMovieFromJSONObject(jMoviesList.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return moviesList;
    }

}
