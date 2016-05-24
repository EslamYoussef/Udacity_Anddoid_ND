package com.udacity.eslam.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udacity.eslam.Models.Movie;
import com.udacity.eslam.R;
import com.udacity.eslam.Utility.Values;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eslam on 5/23/2016.
 */

public class MoviesAdapter extends ArrayAdapter<Movie> {
    Context mContext;
    ArrayList<Movie> mMoviesList;

    public MoviesAdapter(Context context, ArrayList<Movie> moviesList) {
        super(context, R.layout.item_grid_movie, moviesList);
        mContext = context;
        mMoviesList = moviesList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView ivMoviePoster = null;
        if (null == convertView) {
            convertView = ((Activity) (mContext)).getLayoutInflater().inflate(R.layout.item_grid_movie, null, false);
        }
        ivMoviePoster = (ImageView) convertView.findViewById(R.id.ivMoviePoster);
        Movie selectedMovie = mMoviesList.get(position);
        if (null != selectedMovie) {
            String imageURL = Values.BASE_IMG_URL + selectedMovie.getPosterPath();

            Picasso.with(mContext).load(imageURL).placeholder(R.drawable.loading).error(R.drawable.loading).into(ivMoviePoster);
        }
        return convertView;
    }
}
