package com.udacity.eslam.Views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.udacity.eslam.Adapters.MoviesAdapter;
import com.udacity.eslam.Listeners.MovieListener;
import com.udacity.eslam.Listeners.MovieSelectionListener;
import com.udacity.eslam.Models.Movie;
import com.udacity.eslam.Presenters.MoviePresenter;
import com.udacity.eslam.R;
import com.udacity.eslam.Utility.Utilties;
import com.udacity.eslam.Utility.Values;

import java.util.ArrayList;

public class MoviesFragment extends Fragment implements MovieListener {
    private GridView mMoviesGrid;
    private MoviesAdapter mMoviesAdapter;
    private ArrayList<Movie> mMoviesList;
    private MoviePresenter mMoviePrsenter;
    ProgressDialog mProgressDialog;
    boolean isRotated = false;

    public void setMovieSelectionListener(MovieSelectionListener movieSelectionListener) {
        this.mMovieSelectionListener = movieSelectionListener;
    }

    private MovieSelectionListener mMovieSelectionListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_sorting, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.most_popular:
                Utilties.saveUserMovieSortPreference(getActivity(), Values.KEY_MODE_MOST_POPULAR);

                loadMovies();
                return true;
            case R.id.top_rated:
                Utilties.saveUserMovieSortPreference(getActivity(), Values.KEY_MODE_TOP_RATED);
                loadMovies();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragment = inflater.inflate(R.layout.fragment_movies, container, false);
        mMoviesGrid = (GridView) fragment.findViewById(R.id.moviesFragment);
        mMoviesList = new ArrayList<>();
        mMoviesAdapter = new MoviesAdapter(getActivity(), mMoviesList);
        mMoviesGrid.setAdapter(mMoviesAdapter);
        mMoviesGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (null != mMovieSelectionListener) {
                    mMovieSelectionListener.setMovieSelected(mMoviesList.get(position));
                }
            }
        });

        // Init Presenter
        mMoviePrsenter = new MoviePresenter(getActivity(), this);
        //Check if rotated

        loadMovies();

        return fragment;

    }

    private void loadMovies() {
        //Load movies in separate thread
        if (Utilties.isConnected(getActivity())) {
            if (null == mMoviePrsenter)
                mMoviePrsenter = new MoviePresenter(getActivity(), this);
            mMoviePrsenter.loadMovies();
        } else {
            Toast.makeText(getActivity(), R.string.no_connection, Toast.LENGTH_LONG).show();
            getActivity().finish();
        }

    }

    @Override
    public void setMoviesList(ArrayList<Movie> moviesList) {
        if (null != moviesList) {
            mMoviesAdapter.clear();
            mMoviesAdapter.addAll(moviesList);
            mMoviesAdapter.notifyDataSetChanged();
            setActionBarTitle();
        }
    }

    @Override
    public void startLoading() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.show();
    }

    @Override
    public void stopLoading() {
        if (null != mProgressDialog)
            mProgressDialog.cancel();
    }

    private void setActionBarTitle() {
        //set the movie title to the Actionbar
        String sortMode = Utilties.getUserMovieSortPreference(getActivity());
        if (sortMode.equalsIgnoreCase(Values.KEY_MODE_MOST_POPULAR)) {
            ((AppCompatActivity) (getActivity())).getSupportActionBar().setTitle(R.string.most_popular);
        } else if(sortMode.equalsIgnoreCase(Values.KEY_MODE_TOP_RATED)) {
            ((AppCompatActivity) (getActivity())).getSupportActionBar().setTitle(R.string.top_rated);
        }
    }
}
