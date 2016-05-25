package com.udacity.eslam.Tasks;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.udacity.eslam.BuildConfig;
import com.udacity.eslam.Models.Review;
import com.udacity.eslam.Models.Trailer;
import com.udacity.eslam.Utility.URLs;
import com.udacity.eslam.Utility.Values;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Eslam on 5/22/2016.
 */

public class ReviewsTaskLoader extends AsyncTaskLoader<ArrayList<Review>> {
    private ArrayList<Review> mReviewsList;
    private String mURL;
    private Context mContext;
    private Double mMovieId;
    static String LOG_TAG = "Reviews_TASK_LOADER";

    public ReviewsTaskLoader(Context context, Double movieID) {
        super(context);
        mContext = context;
        mMovieId = movieID;
    }

    @Override
    public ArrayList<Review> loadInBackground() {

        Uri builtUri = null;

        builtUri = Uri.parse(URLs.MVDB_BASE_URL + mMovieId.toString() + "/reviews").buildUpon()
                .appendQueryParameter(Values.KEY_API_KEY, BuildConfig.api_key)
                .build();


        URL url = null;
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            url = new URL(builtUri.toString());
            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            //Extracting Movies JSON Array from the response
            JSONArray jReviewsArr = new JSONObject(buffer.toString()).getJSONArray(Values.KEY_RESULTS);
            //Parse the JSON Movies
            mReviewsList = Review.getReviewsListFromJSONArray(jReviewsArr);
            return mReviewsList;
        } catch (IOException e)

        {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        } finally

        {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }


        return null;
    }

}
