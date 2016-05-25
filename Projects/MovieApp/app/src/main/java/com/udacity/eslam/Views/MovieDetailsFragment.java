package com.udacity.eslam.Views;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.eslam.Adapters.TrailersAdapter;
import com.udacity.eslam.Listeners.TrailersListener;
import com.udacity.eslam.Models.Movie;
import com.udacity.eslam.Models.Trailer;
import com.udacity.eslam.Presenters.MoviePresenter;
import com.udacity.eslam.Presenters.TrailersPresenter;
import com.udacity.eslam.R;
import com.udacity.eslam.Utility.Utilties;
import com.udacity.eslam.Utility.Values;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieDetailsFragment extends Fragment implements TrailersListener {
    private Movie mSelectedMovie;

    TextView tvOverview;
    TextView tvReleaseDate;
    ImageView ivMovieBackDrop;
    TextView tvVoteAverage;
    ViewPager vpTrailers;
    private PagerAdapter mPagerAdapter;
    private TrailersPresenter mTrailersPresenter;
    CirclePageIndicator titleIndicator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        vpTrailers = (ViewPager) fragment.findViewById(R.id.vpTrailers);
//Bind the title indicator to the adapter
        titleIndicator = (CirclePageIndicator) fragment.findViewById(R.id.vpIndicator);

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
        //Load Trailers
        mTrailersPresenter = new TrailersPresenter(getActivity(), mSelectedMovie.get_id(), this);
        loadTrailers();
//        mPagerAdapter = new TrailersAdapter(getActivity().getSupportFragmentManager(), new ArrayList<Trailer>());
//        vpTrailers.setAdapter(mPagerAdapter);
        return fragment;
    }

    private void loadTrailers() {
        //Load movies in separate thread
        if (Utilties.isConnected(getActivity())) {
            if (null == mTrailersPresenter)
                mTrailersPresenter = new TrailersPresenter(getActivity(), mSelectedMovie.get_id(), this);
            mTrailersPresenter.loadTrailers();
        } else {
            Toast.makeText(getActivity(), R.string.no_connection, Toast.LENGTH_LONG).show();
            getActivity().finish();
        }

    }


    @Override
    public void setTrailersList(ArrayList<Trailer> trailersList) {
        if (null != trailersList) {
            mPagerAdapter = new TrailersAdapter(getActivity().getSupportFragmentManager(), trailersList);
            vpTrailers.setAdapter(mPagerAdapter);
            titleIndicator.setViewPager(vpTrailers);
        }
    }

}
