package com.example.sergioescoto.popularmovies;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergioescoto on 7/4/16.
 */
public class MovieDeserializer implements JsonDeserializer {

    private static final String LOG_TAG = MovieDeserializer.class.getSimpleName();

    private static final String moviesListKey = "results";

    private static final String genreIdsKey = "genre_ids";

    @Override
    public List<Movie> deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonArray jsonMovieList = json.getAsJsonObject().getAsJsonArray(moviesListKey);

        Gson gson = new Gson();
        List<Movie> movies = new ArrayList<>();

        for(JsonElement jsonMovie : jsonMovieList) {
            Movie movie = gson.fromJson(jsonMovie, Movie.class);

            JsonArray genreIds = jsonMovie.getAsJsonObject().getAsJsonArray(genreIdsKey);
            int[] genre_ids = gson.fromJson(genreIds, int[].class);
            movie.setGenre_ids(genre_ids);

            movie.setPoster_path(movie.getPoster_path().substring(1));

            movies.add(movie);
        }
        return movies;
    }
}