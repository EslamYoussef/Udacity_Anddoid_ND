package com.udacity.eslam.Listeners;

import com.udacity.eslam.Models.Movie;

/**
 * Created by Eslam on 5/25/2016.
 */

public interface MovieSelectionListener {
    void setMovieSelected(Movie selectedMovie);
void refreshDetails();
}
