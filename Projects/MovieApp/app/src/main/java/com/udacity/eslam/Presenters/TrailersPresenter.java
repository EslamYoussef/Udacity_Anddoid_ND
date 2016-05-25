package com.udacity.eslam.Presenters;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

import com.udacity.eslam.Listeners.MovieListener;
import com.udacity.eslam.Listeners.TrailersListener;
import com.udacity.eslam.Models.Movie;
import com.udacity.eslam.Models.Trailer;
import com.udacity.eslam.Tasks.MovieTaskLoader;
import com.udacity.eslam.Tasks.TrailerTaskLoader;

import java.util.ArrayList;

/**
 * Created by Eslam on 5/23/2016.
 */

public class TrailersPresenter implements LoaderManager.LoaderCallbacks<ArrayList<Trailer>> {
    private Context mContext;
    private TrailersListener mTrailersListener;
    private Double mMovieID;
    private static int LOADER_ID = 1;

    public TrailersPresenter(Context context, Double movieID, TrailersListener trailersListener) {
        mContext = context;
        mMovieID = movieID;
        mTrailersListener = trailersListener;
    }

    public void loadTrailers() {
        LoaderManager loaderManager = ((Activity) (mContext)).getLoaderManager();
        if(null!=loaderManager.getLoader(LOADER_ID)&&loaderManager.getLoader(LOADER_ID).isStarted())
            loaderManager.destroyLoader(LOADER_ID);
        loaderManager.initLoader(LOADER_ID, null, this).forceLoad();
    }


    @Override
    public Loader<ArrayList<Trailer>> onCreateLoader(int id, Bundle args) {
        return new TrailerTaskLoader(mContext, mMovieID);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Trailer>> loader, ArrayList<Trailer> data) {

        mTrailersListener.setTrailersList(data);


    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Trailer>> loader) {
        mTrailersListener.setTrailersList(null);

    }
}
