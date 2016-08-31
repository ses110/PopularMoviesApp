package com.example.sergioescoto.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends Activity {

    private static String LOG_TAG = MainActivity.class.getSimpleName();

    final String baseUrl = "https://api.themoviedb.org/3/discover/movie";

    private final String SORT_POPULAR = "popularity.desc";
    private final String SORT_TOP_RATED = "top.desc";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_sort:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String completeApiUrl = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                .appendQueryParameter("sort_by", SORT_POPULAR)
                .build().toString();

        new FetchMovieDataTask().execute(completeApiUrl);

    }

    private void loadMovieData(List<Movie> movies) {
        GridView mMoviePostersGridView = (GridView)findViewById(R.id.moviePostersGridView);
        ImageAdapter moviePostersAdapter = new ImageAdapter(this, movies);
        mMoviePostersGridView.setAdapter(moviePostersAdapter);

        mMoviePostersGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                Movie currentMovie = (Movie) adapterView.getItemAtPosition(pos);

                startActivity(new Intent(MainActivity.this, DetailMovie.class).putExtra(MovieConstants.movieDetail, currentMovie));
            }
        });
    }

    public class FetchMovieDataTask extends AsyncTask<String, Void, List<Movie>> {

        private final String LOG_TAG = FetchMovieDataTask.class.getSimpleName();

        @Override
        protected List<Movie> doInBackground(String... strings) {
            HttpURLConnection apiConnection = null;
            BufferedReader jsonReader = null;

            try {
                URL url = new URL(strings[0]);

                apiConnection = (HttpURLConnection) url.openConnection();
                apiConnection.setRequestMethod("GET");
                apiConnection.connect();

                InputStream inputStream = apiConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if(inputStream == null) {
                    return null;
                }

                jsonReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while((line = jsonReader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if(buffer.length() == 0) {
                    return null;
                }

                List<Movie> listMovies = this.parseMovieJson(buffer.toString());

                return listMovies;

            } catch (IOException e) {
                return null;
            } finally {
                if(apiConnection != null)
                    apiConnection.disconnect();
                if(jsonReader != null) {
                    try {
                        jsonReader.close();
                    } catch (final IOException e) {

                    }
                }
            }
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            loadMovieData(movies);
        }

        private List<Movie> parseMovieJson(String jsonResponse) {
            Type listMovies = new TypeToken<List<Movie>>() {}.getType();

            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(listMovies, new MovieDeserializer())
                    .create();

            List<Movie> allMovies = gson.fromJson(jsonResponse, listMovies);

            for(Movie movie : allMovies) {
                Log.v(LOG_TAG, movie.toString());
            }

            return allMovies;
        }
    }
}
