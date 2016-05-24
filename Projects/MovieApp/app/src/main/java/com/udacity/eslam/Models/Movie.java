package com.udacity.eslam.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.udacity.eslam.Utility.Values;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Eslam on 5/22/2016.
 */

public class Movie implements Parcelable {

    private Double _id;
    private String mTitle;
    private String mOverview;
    private Double mPopularity;
    private String mPosterPath;
    private String mReleaseDate;
    private Double mVoteAverage;
    private Double mVotCount;
    private String mBackDropPath;

    public Movie() {
    }


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
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        this.mPosterPath = posterPath;
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

    public String getBackDropPath() {
        return mBackDropPath;
    }

    public void setBackDropPath(String backDropPath) {
        this.mBackDropPath = backDropPath;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.mReleaseDate = releaseDate;
    }

    public static Movie getMovieFromJSONObject(JSONObject jMovie) throws JSONException {
        Movie movie = new Movie();
        movie.set_id(jMovie.getDouble(Values.KEY_ID));
        movie.setTitle(jMovie.getString(Values.KEY_TITLE));
        movie.setOverview(jMovie.getString(Values.KEY_OVERVIEW));
        movie.setPosterPath(jMovie.getString(Values.KEY_POSTER_PATH));
        movie.setBackDropPath(jMovie.getString(Values.KEY_BACKDROP_PATH));
        movie.setReleaseDate(jMovie.getString(Values.KEY_RELEASE_DATE));
        movie.setPopularity(jMovie.getDouble(Values.KEY_POPULARITY));
        movie.setVotCount(jMovie.getDouble(Values.KEY_VOTE_COUNT));
        movie.setVoteAverage(jMovie.getDouble(Values.KEY_VOTE_AVERAGE));

        return movie;
    }

    public static ArrayList<Movie> getMoviesListFromJSONArray(JSONArray jMoviesList) throws JSONException {
        ArrayList<Movie> moviesList = new ArrayList<>();
        for (int i = 0; i < jMoviesList.length(); i++)
            moviesList.add(getMovieFromJSONObject(jMoviesList.getJSONObject(i)));
        return moviesList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(_id);
        dest.writeString(mTitle);
        dest.writeString(mOverview);
        dest.writeDouble(mPopularity);
        dest.writeString(mPosterPath);
        dest.writeString(mReleaseDate);
        dest.writeDouble(mVoteAverage);
        dest.writeDouble(mVotCount);
        dest.writeString(mBackDropPath);
    }

    // Creator
    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    // "De-parcel object
    public Movie(Parcel in) {
        this._id = in.readDouble();
        this.mTitle = in.readString();
        this.mOverview = in.readString();
        this.mPopularity = in.readDouble();
        this.mPosterPath = in.readString();
        this.mReleaseDate = in.readString();
        this.mVoteAverage = in.readDouble();
        this.mVotCount = in.readDouble();
        this.mBackDropPath = in.readString();
    }

}
