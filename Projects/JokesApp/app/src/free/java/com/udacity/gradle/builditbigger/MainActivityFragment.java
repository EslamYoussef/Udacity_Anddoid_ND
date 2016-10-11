package com.udacity.gradle.builditbigger;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.listeners.JokeListener;
import com.udacity.gradle.builditbigger.tasks.JokesRetrievalTask;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements JokeListener {
    TextView tvJoke;
    Button bTellJoke;
    ProgressDialog mLoadingDialog;
    InterstitialAd mInterstitialAd;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        tvJoke = (TextView) root.findViewById(R.id.instructions_text_view);
        //Ad
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                startLoadingJoke();
            }
        });

        requestNewInterstitial();
        bTellJoke = (Button) root.findViewById(R.id.bTellJoke);
        bTellJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    startLoadingJoke();
                }

            }
        });


        return root;
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    private void startLoadingJoke() {
        new JokesRetrievalTask(MainActivityFragment.this).execute();
    }

    @Override
    public void setJoke(String joke) {
        if (joke.length() <= 0)
            joke = getResources().getString(R.string.no_joke);
        tvJoke.setText(joke);
    }

    @Override
    public void startLoading() {
        mLoadingDialog = new ProgressDialog(getActivity());
        mLoadingDialog.show();
    }

    @Override
    public void endLoading() {
        if (null != mLoadingDialog)
            mLoadingDialog.dismiss();
    }
}
