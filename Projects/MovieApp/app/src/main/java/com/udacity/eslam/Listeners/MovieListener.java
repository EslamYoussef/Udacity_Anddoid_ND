package com.udacity.eslam.Listeners;

import com.udacity.eslam.Models.Movie;

import java.util.ArrayList;

/**
 * Created by Eslam on 5/23/2016.
 */

public interface MovieListener {
    void setMoviesList(ArrayList<Movie> moviesList);

    void startLoading();

    void stopLoading();
}
