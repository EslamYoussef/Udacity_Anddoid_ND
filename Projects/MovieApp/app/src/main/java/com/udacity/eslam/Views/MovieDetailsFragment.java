package com.udacity.eslam.Views;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.eslam.Adapters.ReviewsAdapter;
import com.udacity.eslam.Adapters.TrailersAdapter;
import com.udacity.eslam.Listeners.MovieListener;
import com.udacity.eslam.Listeners.MovieSelectionListener;
import com.udacity.eslam.Listeners.ReviewsListener;
import com.udacity.eslam.Listeners.TrailersListener;
import com.udacity.eslam.Models.Movie;
import com.udacity.eslam.Models.Review;
import com.udacity.eslam.Models.Trailer;
import com.udacity.eslam.Presenters.MoviePresenter;
import com.udacity.eslam.Presenters.ReviewsPresenter;
import com.udacity.eslam.Presenters.TrailersPresenter;
import com.udacity.eslam.R;
import com.udacity.eslam.Utility.Utilties;
import com.udacity.eslam.Utility.Values;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;


public class MovieDetailsFragment extends Fragment implements TrailersListener, ReviewsListener, MovieListener {
    private Movie mSelectedMovie;
    private TextView tvOverview;
    private TextView tvReleaseDate;
    private ImageView ivMovieBackDrop;
    private TextView tvVoteAverage;
    private ViewPager vpTrailers;
    private PagerAdapter mPagerAdapter;
    private TrailersPresenter mTrailersPresenter;
    private ReviewsPresenter mReviewsPresenter;
    private CirclePageIndicator titleIndicator;
    private ListView lvReviews;
    private ReviewsAdapter mReviewAdapter;
    private ImageButton ibFavoriteMovie;
    private boolean mIsFavoritedMovie;
    private MoviePresenter mMoviePresenter;
    private ShareActionProvider mShareActionProvider;
    private ArrayList<Trailer> mMovieTrailers;

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
        tvOverview = (TextView) fragment.findViewById(R.id.tvOverview);
        tvReleaseDate = (TextView) fragment.findViewById(R.id.tvDate);
        ivMovieBackDrop = (ImageView) fragment.findViewById(R.id.ivMovieBackDrop);
        tvVoteAverage = (TextView) fragment.findViewById(R.id.tvVoteAverage);
        vpTrailers = (ViewPager) fragment.findViewById(R.id.vpTrailers);
        lvReviews = (ListView) fragment.findViewById(R.id.lvReviews);
        ibFavoriteMovie = (ImageButton) fragment.findViewById(R.id.ibFavoritedMovie);
        ibFavoriteMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == mMoviePresenter)
                    mMoviePresenter = new MoviePresenter(getActivity(), MovieDetailsFragment.this);
                if (mIsFavoritedMovie) {
                    //UNFavorite Movie
                    if (mMoviePresenter.unFavoriteMovie(mSelectedMovie.get_id())) {
                        mIsFavoritedMovie = false;
                        ibFavoriteMovie.setImageResource(R.drawable.unfavorited);
                        Toast.makeText(getActivity(), R.string.favorite_removed, Toast.LENGTH_LONG).show();
                    }
                } else {
                    //Add Movie to Favorites
                    if (mMoviePresenter.saveMovieAsFavorite(mSelectedMovie)) {
                        mIsFavoritedMovie = true;
                        ibFavoriteMovie.setImageResource(R.drawable.favorited);
                        Toast.makeText(getActivity(), R.string.favorite_Added, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
//      Bind the title indicator to the adapter
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
        //Check if favorite or not
        mMoviePresenter = new MoviePresenter(getActivity(), this);
        //Set the button resource.
        if (mMoviePresenter.isFavoriteMovie(mSelectedMovie.get_id())) {
            mIsFavoritedMovie = true;
            ibFavoriteMovie.setImageResource(R.drawable.favorited);
        } else {
            mIsFavoritedMovie = false;
            ibFavoriteMovie.setImageResource(R.drawable.unfavorited);
        }
        //Load Trailers
        mMovieTrailers = new ArrayList<>();
        mTrailersPresenter = new TrailersPresenter(getActivity(), mSelectedMovie.get_id(), this);
        loadTrailers();

        //Load Reviews
        mReviewsPresenter = new ReviewsPresenter(getActivity(), mSelectedMovie.get_id(), this);

        //Init Reviews list
        mReviewAdapter = new ReviewsAdapter(getActivity(), new ArrayList<Review>());
        lvReviews.setAdapter(mReviewAdapter);
        loadReviews();

        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Re-load favorite movies

    }

    private void loadTrailers() {
        //Load Trailers in separate thread
        if (Utilties.isConnected(getActivity())) {
            if (null == mTrailersPresenter)
                mTrailersPresenter = new TrailersPresenter(getActivity(), mSelectedMovie.get_id(), this);
            mTrailersPresenter.loadTrailers();
        } else {
            Toast.makeText(getActivity(), R.string.no_connection, Toast.LENGTH_LONG).show();
            getActivity().finish();
        }

    }

    private void loadReviews() {
        //Load Reviews in separate thread
        if (Utilties.isConnected(getActivity())) {
            if (null == mReviewsPresenter)
                mReviewsPresenter = new ReviewsPresenter(getActivity(), mSelectedMovie.get_id(), this);
            mReviewsPresenter.loadReviews();
        } else {
            Toast.makeText(getActivity(), R.string.no_connection, Toast.LENGTH_LONG).show();
            getActivity().finish();
        }

    }

    @Override
    public void setTrailersList(ArrayList<Trailer> trailersList) {
        if (null != trailersList) {
            mMovieTrailers = trailersList;
            mPagerAdapter = new TrailersAdapter(getActivity().getSupportFragmentManager(), mMovieTrailers);
            vpTrailers.setAdapter(mPagerAdapter);
            titleIndicator.setViewPager(vpTrailers);
        }
//        // Now share option should be ready
//        setShareIntent(createShareIntent());
        try {
            this.getActivity().invalidateOptionsMenu();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void setReviewsList(ArrayList<Review> reviewsList) {
        if (null != reviewsList) {
            mReviewAdapter.clear();
            mReviewAdapter.addAll(reviewsList);
            mReviewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setMoviesList(ArrayList<Movie> moviesList) {

    }

    @Override
    public void startLoading() {

    }

    @Override
    public void stopLoading() {

    }

    //    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        // Inflate menu resource file.
//        inflater.inflate(R.menu.movie_details_options, menu);
//
//        // Locate MenuItem with ShareActionProvider
//        MenuItem item = menu.findItem(R.id.menu_item_share);
//
//        // Fetch and store ShareActionProvider
//        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
//        setShareIntent(createShareIntent());
//    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movie_details_options, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_item_share);
        ShareActionProvider mShareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(createShareIntent());
        } else
            Log.d("", "Share action provider is null");
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private Intent createShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//        if (mSelectedMovie != null)

        if (mMovieTrailers != null && mMovieTrailers.size() > 0)
            shareIntent.putExtra(Intent.EXTRA_TEXT,
                    Values.BASE_YOUTUBE + mMovieTrailers.get(0).getKey());
        return shareIntent;
    }
}
