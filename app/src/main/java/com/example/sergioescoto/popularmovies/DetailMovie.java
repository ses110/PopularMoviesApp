package com.example.sergioescoto.popularmovies;

import android.app.Activity;
import android.os.Bundle;

public class DetailMovie extends Activity {

    private final String LOG_TAG = DetailMovie.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Movie someMovie = extras.getParcelable(MovieConstants.movieDetail);
        }

    }
}
