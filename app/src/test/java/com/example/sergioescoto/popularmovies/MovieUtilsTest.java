package com.example.sergioescoto.popularmovies;

import android.content.Context;
import android.net.Uri;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by sergioescoto on 9/3/16.
 */

@PrepareForTest({Uri.class})
@RunWith(PowerMockRunner.class)
public class MovieUtilsTest {
    @Mock Context mMockContext;

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(Uri.class);
        //Uri uri = PowerMockito.mock(Uri.class);
        Uri uri = Uri.parse("https://api.themoviedb.org/3/discover/movie?api_key=fcae63a0084cbaed783adf8d8a4770f7&sort_by=vote_average.desc");

        PowerMockito.when(Uri.class, "parse", Mockito.anyString()).thenReturn(uri);
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
