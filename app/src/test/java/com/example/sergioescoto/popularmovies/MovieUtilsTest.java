package com.example.sergioescoto.popularmovies;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by sergioescoto on 9/3/16.
 */
public class MovieUtilsTest {


    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getBaseUrl_test() {
//        String
    }

    @Test
    public void getTopMovies() {
        final String topMoviesUrl =  "https://api.themoviedb.org/3/discover/movie?api_key=fcae63a0084cbaed783adf8d8a4770f7&sort_by=popularity.desc&sort_by=top.desc";
        assertEquals(MovieUtils.getTopMovies(), topMoviesUrl);
    }

    @Test
    public void getPopularMovies() {
        final String popularMoviesUrl =  "https://api.themoviedb.org/3/discover/movie?api_key=fcae63a0084cbaed783adf8d8a4770f7&sort_by=popularity.desc&sort_by=top.desc";
        assertEquals(MovieUtils.getPopularMovies(), popularMoviesUrl);

    }
}
