package com.udacity.gradle.builditbigger.listeners;

/**
 * Created by Eslam on 10/4/2016.
 */

public interface JokeListener {
    void setJoke(String joke);
    void startLoading();
    void endLoading();
}
