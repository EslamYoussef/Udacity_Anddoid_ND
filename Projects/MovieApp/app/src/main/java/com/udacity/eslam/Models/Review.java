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

public class Review implements Parcelable {
    private String _id;
    private String mAuthor;
    private String mContent;
    private String mURL;

    public String getURL() {
        return mURL;
    }

    public void setURL(String uRL) {
        this.mURL = uRL;
    }


    public Review() {
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        this.mAuthor = author;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }


    public static Review getReviewFromJSONObject(JSONObject jReview) throws JSONException {
        Review review = new Review();
        review.set_id(jReview.getString(Values.KEY_ID));
        review.setAuthor(jReview.getString(Values.KEY_AUTHOR));
        review.setContent(jReview.getString(Values.KEY_CONTENT));
        review.setURL(jReview.getString(Values.KEY_URL));
        return review;
    }

    public static ArrayList<Review> getReviewsListFromJSONArray(JSONArray jReviewsArr) throws JSONException {
        ArrayList<Review> reviewsList = new ArrayList<>();
        for (int i = 0; i < jReviewsArr.length(); i++) {
            reviewsList.add(getReviewFromJSONObject(jReviewsArr.getJSONObject(i)));
        }
        return reviewsList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(mAuthor);
        dest.writeString(mContent);
        dest.writeString(mURL);
    }

    // Creator
    public static final Creator CREATOR
            = new Creator() {
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    // "De-parcel object
    public Review(Parcel in) {
        this._id = in.readString();
        this.mAuthor = in.readString();
        this.mContent = in.readString();
        this.mURL = in.readString();

    }

}
