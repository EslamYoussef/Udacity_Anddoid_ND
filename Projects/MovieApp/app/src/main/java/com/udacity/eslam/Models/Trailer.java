package com.udacity.eslam.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.udacity.eslam.Utility.Values;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Eslam on 5/25/2016.
 */

public class Trailer implements Parcelable {
    private String _id;
    private String mName;
    private String mKey;

    public Trailer() {
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        this.mKey = key;
    }

    public String getName() {
        return mName;
    }

    public void setmName(String name) {
        this.mName = name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }


    public static Trailer getTrailerFromJSONObject(JSONObject jTrailer) throws JSONException {
        Trailer trailer = new Trailer();
        trailer.set_id(jTrailer.getString(Values.KEY_ID));
        trailer.setmName(jTrailer.getString(Values.KEY_NAME));
        trailer.setKey(jTrailer.getString(Values.KEY_TRAILER_KEY));
        return trailer;
    }

    public static ArrayList<Trailer> getTrailersListFromJSONArray(JSONArray jTrailersArr) throws JSONException {
        ArrayList<Trailer> trailersList = new ArrayList<>();
        for (int i = 0; i < jTrailersArr.length(); i++) {
            trailersList.add(getTrailerFromJSONObject(jTrailersArr.getJSONObject(i)));
        }
        return trailersList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(mName);
        dest.writeString(mKey);
    }

    // Creator
    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    // "De-parcel object
    public Trailer(Parcel in) {
        this._id = in.readString();
        this.mName = in.readString();
        this.mKey = in.readString();

    }

}
