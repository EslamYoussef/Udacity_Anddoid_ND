package com.udacity.eslam.Tasks;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.udacity.eslam.Models.Movie;

import java.util.ArrayList;

/**
 * Created by Eslam on 5/22/2016.
 */

public class MovieTaskLoader extends AsyncTaskLoader<ArrayList<Movie>> {
    private ArrayList<Movie> mMoviesList;
    private String mURL;
    private Context mContext;

    public MovieTaskLoader(Context context) {
        super(context);
        mContext = context;
    }

    public MovieTaskLoader(Context context, String url) {
        super(context);
        mContext = context;
        mURL = url;
    }

    @Override
    public ArrayList<Movie> loadInBackground() {

        return null;
    }

    /**
     * Called when there is new data to deliver to the client.  The
     * super class will take care of delivering it; the implementation
     * here just adds a little more logic.
     */
    @Override
    public void deliverResult(ArrayList<Movie> moviesList) {
        if (isReset()) {
            // An async query came in while the loader is stopped.  We
            // don't need the result.
            if (moviesList != null) {
                onReleaseResources(moviesList);
            }
        }
        ArrayList<Movie> oldMovies = mMoviesList;
        mMoviesList = moviesList;

        if (isStarted()) {
            // If the Loader is currently started, we can immediately
            // deliver its results.
            super.deliverResult(moviesList);
        }

        // At this point we can release the resources associated with
        // 'oldMovies' if needed; now that the new result is delivered we
        // know that it is no longer in use.
        if (oldMovies != null) {
            onReleaseResources(oldMovies);
        }
    }

    /**
     * Handles a request to start the Loader.
     */
    @Override
    protected void onStartLoading() {
        if (mMoviesList != null) {
            // If we currently have a result available, deliver it
            // immediately.
            deliverResult(mMoviesList);
        }

        forceLoad();

    }

    /**
     * Handles a request to stop the Loader.
     */
    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    /**
     * Handles a request to cancel a load.
     */
    @Override
    public void onCanceled(ArrayList<Movie> moviesList) {
        super.onCanceled(moviesList);

        // At this point we can release the resources associated with 'apps'
        // if needed.
        onReleaseResources(moviesList);
    }

    /**
     * Handles a request to completely reset the Loader.
     */
    @Override
    protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();

        // At this point we can release the resources associated with 'apps'
        // if needed.
        if (mMoviesList != null) {
            onReleaseResources(mMoviesList);
            mMoviesList = null;
        }


    }

    /**
     * Helper function to take care of releasing resources associated
     * with an actively loaded data set.
     */
    protected void onReleaseResources(ArrayList<Movie> moviesList) {
        // For a simple List<> there is nothing to do.  For something
        // like a Cursor, we would close it here.
    }
}
