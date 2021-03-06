package com.udacity.eslam.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.udacity.eslam.R;

/**
 * Created by Eslam on 5/23/2016.
 */

public class Utilties {
    public static boolean saveSharedPreferences(Context context, String key, String value) {
        SharedPreferences prefs = context.getSharedPreferences(Values.KEY_PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String getStringFromSharedPreferences(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(Values.KEY_PREF_FILE, Context.MODE_PRIVATE);
        return prefs.getString(key, context.getResources().getString(R.string.most_popular));
    }

    public static boolean saveUserMovieSortPreference(Context context, String value) {
        return saveSharedPreferences(context, Values.KEY_SORT_MODE, value);
    }

    public static String getUserMovieSortPreference(Context context) {
        return getStringFromSharedPreferences(context, Values.KEY_SORT_MODE);
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
