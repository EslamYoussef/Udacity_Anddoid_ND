package com.udacity.eslam.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.udacity.eslam.Models.Trailer;
import com.udacity.eslam.Utility.Values;
import com.udacity.eslam.Views.TrailerFragment;

import java.util.ArrayList;

/**
 * Created by Eslam on 5/25/2016.
 */

public class TrailersAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Trailer> mTrailersList;

    public TrailersAdapter(FragmentManager fm, ArrayList<Trailer> trailers) {
        super(fm);
        mTrailersList = trailers;
    }

    @Override
    public Fragment getItem(int position) {
        TrailerFragment trailerFragment = new TrailerFragment();
        Bundle extras = new Bundle();
        //Add trailers data to the extras bundle
        extras.putParcelable(Values.KEY_TRAILER, mTrailersList.get(position));
        trailerFragment.setArguments(extras);
        return trailerFragment;
    }

    @Override
    public int getCount() {
        return mTrailersList.size();
    }
}
