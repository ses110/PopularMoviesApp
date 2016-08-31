package com.example.sergioescoto.popularmovies;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailMovie extends Activity {

    private final String LOG_TAG = DetailMovie.class.getSimpleName();

    private Movie detailMovie;

    private static String base_path = "http://image.tmdb.org/t/p";

    private static String imageSize = "w185";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            detailMovie = extras.getParcelable(MovieConstants.movieDetail);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(detailMovie != null) {
            TextView movieTitle = (TextView) findViewById(R.id.movie_title);
            TextView movieFullDescription = (TextView) findViewById(R.id.movie_full_description);

            TextView movieYear = (TextView) findViewById(R.id.movie_year);
            TextView movieLength = (TextView) findViewById(R.id.movie_length);
            TextView movieRating = (TextView) findViewById(R.id.movie_rating);

            movieTitle.setText(detailMovie.getMovieTitle());
            movieFullDescription.setText(detailMovie.getOverview());
            movieYear.setText(detailMovie.getRelease_date());
            movieLength.setText("120min");
            movieRating.setText(Double.toString(detailMovie.getVote_average()) + "/10");

            ImageView moviePoster = (ImageView) findViewById(R.id.movie_poster);

            String fullImagePath = Uri.parse(base_path).buildUpon().appendPath(imageSize).appendPath(detailMovie.getPoster_path()).build().toString();

            Picasso.with(this).load(fullImagePath).fit().into(moviePoster);
        }
    }
}
