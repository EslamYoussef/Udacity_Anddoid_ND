package com.udacity.eslam.Views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.eslam.Models.Trailer;
import com.udacity.eslam.R;
import com.udacity.eslam.Utility.Values;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TrailerFragment extends Fragment {

    TextView tvTrailerTitle;
    ImageView ivTrailerImage;
    private Trailer mTrailer;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_trailer, container, false);
        tvTrailerTitle = (TextView) fragment.findViewById(R.id.tvTrailerTitle);
        ivTrailerImage = (ImageView) fragment.findViewById(R.id.ivTrailerImage);
        mTrailer = getArguments().getParcelable(Values.KEY_TRAILER);
        if (null != mTrailer) {
            tvTrailerTitle.setText(mTrailer.getName());

            ivTrailerImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(Values.BASE_YOUTUBE + mTrailer.getKey()));
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), R.string.cannot_play_trailer, Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
        return fragment;
    }
}
