package com.example.sergioescoto.popularmovies;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private static String LOG_TAG = MainActivity.class.getSimpleName();

    private SharedPreferences sharedPref;

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
                AlertDialog.Builder sortOptionsDialog = new AlertDialog.Builder(this);
                sortOptionsDialog.setTitle(getString(R.string.dialog_sort_movies_title));
                sortOptionsDialog.setItems(getResources().getStringArray(R.array.sort_movies_options),
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor optionsEditor = sharedPref.edit();
                        switch(which) {
                            case 0:
                                optionsEditor.putString(getString(R.string.movie_url_setting), MovieUtils.getTopMovies());
                                break;
                            case 1:
                                optionsEditor.putString(getString(R.string.movie_url_setting), MovieUtils.getPopularMovies());
                                break;
                        }
                        optionsEditor.commit();
                        MainActivity.this.fetchMovieData();
                    }
                });
                sortOptionsDialog.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.fetchMovieData();
    }

    private void fetchMovieData() {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        String moviesURL = sharedPref.getString(getString(R.string.movie_url_setting), MovieUtils.getPopularMovies());
        Log.v(LOG_TAG, "Movies URL = " + moviesURL);

        new FetchMovieDataTask().execute(moviesURL);
    }

    private void displayMovieData(List<Movie> movies) {
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
            List<Movie> listMovies = new ArrayList<Movie>();
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

                listMovies = this.parseMovieJson(buffer.toString());

                return listMovies;

            } catch (IOException e) {
                Log.e(LOG_TAG, "FetchMovieDataTask :: Device is offline");
                return listMovies;
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
            displayMovieData(movies);
        }

        private List<Movie> parseMovieJson(String jsonResponse) {
            Type listMovies = new TypeToken<List<Movie>>() {}.getType();

            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(listMovies, new MovieDeserializer())
                    .create();

            List<Movie> allMovies = gson.fromJson(jsonResponse, listMovies);

            return allMovies;
        }
    }
}
