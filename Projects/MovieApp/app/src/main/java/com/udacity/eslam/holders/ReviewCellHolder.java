package com.udacity.eslam.holders;

import android.widget.TextView;

/**
 * Created by Eslam on 5/25/2016.
 */

public class ReviewCellHolder {
    public TextView getTvAuthor() {
        return tvAuthor;
    }

    public void setTvAuthor(TextView tvAuthor) {
        this.tvAuthor = tvAuthor;
    }

    private TextView tvAuthor;

    public TextView getTvContent() {
        return tvContent;
    }

    public void setTvContent(TextView tvContent) {
        this.tvContent = tvContent;
    }

    private TextView tvContent;
}
