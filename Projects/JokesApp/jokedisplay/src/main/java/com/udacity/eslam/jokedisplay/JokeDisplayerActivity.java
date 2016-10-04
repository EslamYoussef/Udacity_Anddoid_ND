package com.udacity.eslam.jokedisplay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeDisplayerActivity extends AppCompatActivity {
    TextView tvJoke;
    String jokeStr = "";
    public static String KEY_JOKE = "joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_displayer);
        //Receive the sent joke
        if (null != getIntent()) {
            Bundle extras = getIntent().getExtras();
            if (null != extras) {
                jokeStr = extras.getString(KEY_JOKE);
            }
        }
        if (jokeStr.length() <= 0) {
            jokeStr = getResources().getString(R.string.no_joke_received);
        }
        tvJoke.setText(jokeStr);

    }
}
