package com.udacity.eslam.Utility;

import android.content.Context;
import android.content.SharedPreferences;

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
        return prefs.getString(key, Values.KEY_MODE_MOST_POPULAR);
    }

    public static String getUserMovieSortPreference(Context context) {
        return getStringFromSharedPreferences(context, Values.KEY_SORT_MODE);
    }
}
