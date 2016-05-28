package com.udacity.eslam.Views;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.udacity.eslam.Listeners.MovieSelectionListener;
import com.udacity.eslam.Models.Movie;
import com.udacity.eslam.R;
import com.udacity.eslam.Utility.Values;


class MainActivity extends AppCompatActivity implements MovieSelectionListener {
    FrameLayout flMoviesFragment;
    private String FRAGMENT_TAG = "movie_fragment";

    MovieDetailsFragment mFragment;
    private boolean mIsTwoPaneUI = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MoviesFragment mMoviesFragment = (MoviesFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);

        if (null == mMoviesFragment) {
            mMoviesFragment = new MoviesFragment();
            mMoviesFragment.setMovieSelectionListener(this);
            getSupportFragmentManager().beginTransaction().add(R.id.flMainFragmentContainer, mMoviesFragment, FRAGMENT_TAG).commit();
        }

        if (null == (FrameLayout) findViewById(R.id.flDetailsFragmentContainer))
            mIsTwoPaneUI = false;
    }

    @Override
    public void setMovieSelected(Movie selectedMovie) {
        if (mIsTwoPaneUI) {

            mFragment = new MovieDetailsFragment();
            //Put the selected movie as extra

            Bundle extras = new Bundle();
            extras.putParcelable(Values.KEY_MOVIE, selectedMovie);
            mFragment.setArguments(extras);
            getSupportFragmentManager().beginTransaction().replace(R.id.flDetailsFragmentContainer, mFragment).commit();
        } else {
            // Open DetailsActivity
            Intent i = new Intent(this, DetailsActivity.class);
            // Pass the selected movie to the Movie Details Activity
            i.putExtra(Values.KEY_MOVIE, selectedMovie);
            startActivity(i);
        }
    }




}
