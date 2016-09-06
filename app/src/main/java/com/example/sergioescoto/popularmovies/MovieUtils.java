package com.example.sergioescoto.popularmovies;

import android.net.Uri;

/**
 * Created by sergioescoto on 9/3/16.
 */
public class MovieUtils {
    private static final String baseUrl = "https://api.themoviedb.org/3/discover/movie";

    private static final Uri completeApiUrl = Uri.parse(baseUrl).buildUpon()
            .appendQueryParameter("api_key", BuildConfig.TMDB_API_KEY).build();

    private static final String SORT_POPULAR = "popularity.desc";

    private static final String SORT_TOP_RATED = "vote_average.desc";

    private MovieUtils() {}

    public static String getBaseUrl() {
        return completeApiUrl.toString();
    }

    public static String getTopMovies() {
        return completeApiUrl.buildUpon().appendQueryParameter("sort_by", SORT_TOP_RATED).toString();
    }

    public static String getPopularMovies() {
        return completeApiUrl.buildUpon().appendQueryParameter("sort_by", SORT_POPULAR).toString();
    }
}
