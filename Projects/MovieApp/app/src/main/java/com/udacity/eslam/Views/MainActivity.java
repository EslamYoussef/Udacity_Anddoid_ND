package com.udacity.eslam.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.udacity.eslam.R;

public class MainActivity extends AppCompatActivity {
    FrameLayout flMoviesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //Inflate the Movies Fragment
        getSupportFragmentManager().beginTransaction().add(R.id.flMainFragmentContainer, new MoviesFragment()).commit();

    }
}
