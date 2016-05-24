package com.udacity.eslam.Views;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.udacity.eslam.Models.Movie;
import com.udacity.eslam.R;

public class MainActivity extends AppCompatActivity {
    FrameLayout flMoviesFragment;
    private String FRAGMENT_TAG = "movie_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MoviesFragment mMoviesFragment = (MoviesFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (null == mMoviesFragment)
            getSupportFragmentManager().beginTransaction().add(R.id.flMainFragmentContainer, new MoviesFragment(),FRAGMENT_TAG).commit();
//        else
//            getSupportFragmentManager().beginTransaction().replace(R.id.flMainFragmentContainer, mMoviesFragment).commit();
    }
}
