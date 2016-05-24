package com.udacity.eslam.Presenters;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

import com.udacity.eslam.Listeners.MovieListener;
import com.udacity.eslam.Models.Movie;
import com.udacity.eslam.Tasks.MovieTaskLoader;

import java.util.ArrayList;

/**
 * Created by Eslam on 5/23/2016.
 */

public class MoviePresenter implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {
    private Context mContext;
    private MovieListener mMovieListener;

    private static int LOADER_ID = 0;

    public MoviePresenter(Context context, MovieListener movieListener) {
        mContext = context;
        mMovieListener = movieListener;
    }

    public void loadMovies() {
        LoaderManager loaderManager = ((Activity) (mContext)).getLoaderManager();
//        if (null == loaderManager.getLoader(LOADER_ID) || !loaderManager.getLoader(LOADER_ID).isStarted()) {
            loaderManager.initLoader(LOADER_ID, null, this).forceLoad();
            mMovieListener.startLoading();
//        }
    }


    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
        return new MovieTaskLoader(mContext);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {

        mMovieListener.setMoviesList(data);
        mMovieListener.stopLoading();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
        mMovieListener.setMoviesList(null);
        mMovieListener.stopLoading();

    }
}
