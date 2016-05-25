package com.udacity.eslam.Presenters;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

import com.udacity.eslam.Listeners.ReviewsListener;
import com.udacity.eslam.Listeners.TrailersListener;
import com.udacity.eslam.Models.Review;
import com.udacity.eslam.Models.Trailer;
import com.udacity.eslam.Tasks.ReviewsTaskLoader;
import com.udacity.eslam.Tasks.TrailerTaskLoader;

import java.util.ArrayList;

/**
 * Created by Eslam on 5/23/2016.
 */

public class ReviewsPresenter implements LoaderManager.LoaderCallbacks<ArrayList<Review>> {
    private Context mContext;
    private ReviewsListener mReviewsListener;
    private Double mMovieID;
    private static int LOADER_ID = 2;

    public ReviewsPresenter(Context context, Double movieID, ReviewsListener reviewsListener) {
        mContext = context;
        mMovieID = movieID;
        mReviewsListener = reviewsListener;
    }

    public void loadReviews() {
        LoaderManager loaderManager = ((Activity) (mContext)).getLoaderManager();
        loaderManager.initLoader(LOADER_ID, null, this).forceLoad();
    }


    @Override
    public Loader<ArrayList<Review>> onCreateLoader(int id, Bundle args) {
        return new ReviewsTaskLoader(mContext, mMovieID);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Review>> loader, ArrayList<Review> data) {

        mReviewsListener.setReviewsList(data);

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Review>> loader) {
        mReviewsListener.setReviewsList(null);

    }
}
