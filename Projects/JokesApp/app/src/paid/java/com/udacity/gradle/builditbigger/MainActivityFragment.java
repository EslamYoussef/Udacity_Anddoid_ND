package com.udacity.gradle.builditbigger;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.udacity.gradle.builditbigger.listeners.JokeListener;
import com.udacity.gradle.builditbigger.tasks.JokesRetrievalTask;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements JokeListener {
    TextView tvJoke;
    Button bTellJoke;
    ProgressDialog mLoadingDialog;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        tvJoke = (TextView) root.findViewById(R.id.instructions_text_view);
        bTellJoke = (Button) root.findViewById(R.id.bTellJoke);
        bTellJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new JokesRetrievalTask(MainActivityFragment.this).execute();
            }
        });
        return root;
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
