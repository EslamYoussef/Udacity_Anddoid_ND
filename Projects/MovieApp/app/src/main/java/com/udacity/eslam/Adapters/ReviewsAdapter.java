package com.udacity.eslam.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.udacity.eslam.Models.Review;
import com.udacity.eslam.R;
import com.udacity.eslam.holders.ReviewCellHolder;

import java.util.ArrayList;

/**
 * Created by Eslam on 5/23/2016.
 */

public class ReviewsAdapter extends ArrayAdapter<Review> {
    Context mContext;
    ArrayList<Review> mReviewsList;

    public ReviewsAdapter(Context context, ArrayList<Review> reviewsList) {
        super(context, R.layout.item_grid_movie, reviewsList);
        mContext = context;
        mReviewsList = reviewsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReviewCellHolder holder = new ReviewCellHolder();
        if (null == convertView) {
            convertView = ((Activity) (mContext)).getLayoutInflater().inflate(R.layout.item_list_review, null, false);
            holder.setTvAuthor((TextView) convertView.findViewById(R.id.tvAuthor));
            holder.setTvContent((TextView) convertView.findViewById(R.id.tvContent));
            convertView.setTag(holder);
        } else {
            holder = (ReviewCellHolder) convertView.getTag();
        }
        Review selectedReview = mReviewsList.get(position);
        if (null != selectedReview && null != holder) {
            holder.getTvAuthor().setText(selectedReview.getAuthor());
            holder.getTvContent().setText(selectedReview.getContent());
        }
        return convertView;
    }
}
