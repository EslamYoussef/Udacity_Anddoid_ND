package com.udacity.eslam.Tasks;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.udacity.eslam.BuildConfig;
import com.udacity.eslam.Models.Movie;
import com.udacity.eslam.Models.Trailer;
import com.udacity.eslam.Utility.URLs;
import com.udacity.eslam.Utility.Utilties;
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

public class TrailerTaskLoader extends AsyncTaskLoader<ArrayList<Trailer>> {
    private ArrayList<Trailer> mTrailersList;
    private String mURL;
    private Context mContext;
    private Double mMovieId;
    static String LOG_TAG = "TRAILER_TASK_LOADER";

    public TrailerTaskLoader(Context context, Double movieID) {
        super(context);
        mContext = context;
        mMovieId = movieID;
    }

    @Override
    public ArrayList<Trailer> loadInBackground() {

        Uri builtUri = null;
        builtUri = Uri.parse(URLs.MVDB_BASE_URL + mMovieId.toString() + "/videos").buildUpon()
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
            JSONArray jTrailersArray = new JSONObject(buffer.toString()).getJSONArray(Values.KEY_RESULTS);
            //Parse the JSON Movies
            mTrailersList = Trailer.getTrailersListFromJSONArray(jTrailersArray);
            return mTrailersList;
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

    /**
     * Called when there is new data to deliver to the client.  The
     * super class will take care of delivering it; the implementation
     * here just adds a little more logic.
     */
    @Override
    public void deliverResult(ArrayList<Trailer> trailersList) {
        if (isReset()) {
            // An async query came in while the loader is stopped.  We
            // don't need the result.
            if (trailersList != null) {
                onReleaseResources(trailersList);
            }
        }
        ArrayList<Trailer> oldTrailers = mTrailersList;
        mTrailersList = trailersList;

        if (isStarted()) {
            // If the Loader is currently started, we can immediately
            // deliver its results.
            super.deliverResult(trailersList);
        }

        // At this point we can release the resources associated with
        // 'oldTrailers' if needed; now that the new result is delivered we
        // know that it is no longer in use.
        if (oldTrailers != null) {
            onReleaseResources(oldTrailers);
        }
    }

    /**
     * Handles a request to start the Loader.
     */
    @Override
    protected void onStartLoading() {
//        if (mTrailersList != null) {
//            // If we currently have a result available, deliver it
//            // immediately.
//            deliverResult(mTrailersList);
//        }

        forceLoad();

    }

    /**
     * Handles a request to stop the Loader.
     */
    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    /**
     * Handles a request to cancel a load.
     */
    @Override
    public void onCanceled(ArrayList<Trailer> trailersList) {
        super.onCanceled(trailersList);

        // At this point we can release the resources associated with 'apps'
        // if needed.
        onReleaseResources(trailersList);
    }

    /**
     * Handles a request to completely reset the Loader.
     */
    @Override
    protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();

        // At this point we can release the resources associated with 'apps'
        // if needed.
        if (mTrailersList != null) {
            onReleaseResources(mTrailersList);
            mTrailersList = null;
        }


    }

    /**
     * Helper function to take care of releasing resources associated
     * with an actively loaded data set.
     */
    protected void onReleaseResources(ArrayList<Trailer> trailersList) {
        // For a simple List<> there is nothing to do.  For something
        // like a Cursor, we would close it here.
    }
}
