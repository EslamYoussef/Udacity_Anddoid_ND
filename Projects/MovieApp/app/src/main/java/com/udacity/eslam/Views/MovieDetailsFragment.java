package com.udacity.eslam.Views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.eslam.Models.Movie;
import com.udacity.eslam.R;
import com.udacity.eslam.Utility.Values;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieDetailsFragment extends Fragment {
    private Movie mSelectedMovie;
    @BindView(R.id.tvOverview)
    TextView tvOverview;
    @BindView(R.id.tvDate)
    TextView tvReleaseDate;
    @BindView(R.id.ivMovieBackDrop)
    ImageView ivMovieBackDrop;

    @BindView(R.id.tvVoteAverage)
    TextView tvVoteAverage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_movie_details, container, false);
        // Receive movie details
        Bundle extras = getArguments();
        mSelectedMovie = extras.getParcelable(Values.KEY_MOVIE);
        //Init Views
//        tvTitle = (TextView) fragment.findViewById(R.id.tvTitle);
        ButterKnife.bind(this, fragment);
        tvOverview = (TextView) fragment.findViewById(R.id.tvOverview);
        tvReleaseDate = (TextView) fragment.findViewById(R.id.tvDate);
        ivMovieBackDrop = (ImageView) fragment.findViewById(R.id.ivMovieBackDrop);
        tvVoteAverage = (TextView) fragment.findViewById(R.id.tvVoteAverage);


        if (null != mSelectedMovie) {
            //Load Movie backdrop image
            String imageURL = Values.BASE_IMG_URL + mSelectedMovie.getBackDropPath();
            Picasso.with(getActivity()).load(imageURL).placeholder(R.drawable.loading).error(R.drawable.loading).into(ivMovieBackDrop);
//            tvTitle.setText(mSelectedMovie.getTitle());
            //set the movie title to the Actionbar
            ((AppCompatActivity) (getActivity())).getSupportActionBar().setTitle(mSelectedMovie.getTitle());
            tvOverview.setText(mSelectedMovie.getOverview());
            tvReleaseDate.setText(getResources().getString(R.string.release_date) + ": " + mSelectedMovie.getReleaseDate());
            tvVoteAverage.setText(getResources().getString(R.string.rating) + "" + mSelectedMovie.getVoteAverage().toString() + " / 10");

        }
        return fragment;
    }


}
