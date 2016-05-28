package com.udacity.eslam.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.udacity.eslam.Models.Movie;

import java.util.ArrayList;

/**
 * Created by Eslam on 5/26/2016.
 */

public class MovieDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "movies_db";
    private static Integer DB_VERSION = 1;

    public MovieDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_LOCATION_Movie = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" +
                MovieContract.MovieEntry.COLUMN_ID + " INTEGER PRIMARY KEY," +
                MovieContract.MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_BACKDROP_PATH + " REAL NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_VOTE_AVG + " REAL NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_POPULARITY + " REAL NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_VOTE_COUNT + " NUMBER NOT NULL )";
        sqLiteDatabase.execSQL(SQL_CREATE_LOCATION_Movie);

    }

    public long insertMovie(Movie movie) {

        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry.COLUMN_ID, movie.get_id());
        values.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
        values.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        values.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        values.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        values.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH, movie.getBackDropPath());
        values.put(MovieContract.MovieEntry.COLUMN_VOTE_AVG, movie.getVoteAverage());
        values.put(MovieContract.MovieEntry.COLUMN_VOTE_COUNT, movie.getVotCount());
        values.put(MovieContract.MovieEntry.COLUMN_POPULARITY, movie.getPopularity());

        //GET DB instanc
        SQLiteDatabase db = this.getWritableDatabase();
        long insertedRowID = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
        db.close();
        return insertedRowID;

    }

    public Movie getMovieByID(Double movieID) {
        Movie movie = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(MovieContract.MovieEntry.TABLE_NAME, null, MovieContract.MovieEntry.COLUMN_ID + "=?", new String[]{movieID.toString()}, null, null, null);
        ArrayList<Movie> moviesList = getMoviesListFromCursor(cursor);
        if (moviesList.size() > 0) {
            movie = moviesList.get(0);
        }

        return movie;
    }

    public ArrayList<Movie> getAllMovies() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(MovieContract.MovieEntry.TABLE_NAME, null, null, null, null, null, null);

        return getMoviesListFromCursor(cursor);
    }

    private ArrayList<Movie> getMoviesListFromCursor(Cursor cursor) {
        ArrayList<Movie> moviesList = new ArrayList<>();
        Movie movie = null;
        if (null != cursor && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                movie = new Movie();
                movie.set_id(cursor.getDouble(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH)));
                movie.setBackDropPath(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE)));
                movie.setPopularity(cursor.getDouble(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POPULARITY)));
                movie.setVotCount(cursor.getDouble(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_COUNT)));
                movie.setVoteAverage(cursor.getDouble(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_AVG)));
                moviesList.add(movie);
            } while (cursor.moveToNext());

        }
        cursor.close();
        return moviesList;
    }

    public int removeMovie(Double movieID) {
        SQLiteDatabase db = this.getWritableDatabase();
        int numberOfDeletedRows = db.delete(MovieContract.MovieEntry.TABLE_NAME, MovieContract.MovieEntry.COLUMN_ID + "=?", new String[]{movieID.toString()});
        db.close();
        return numberOfDeletedRows;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
