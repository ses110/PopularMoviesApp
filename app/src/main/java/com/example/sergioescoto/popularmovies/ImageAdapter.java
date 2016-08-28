package com.example.sergioescoto.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sergioescoto on 6/8/16.
 */
public class ImageAdapter extends ArrayAdapter<Movie> {
    private static String LOG_TAG = ImageAdapter.class.getSimpleName();

    private Context mContext;

    private static String base_path = "http://image.tmdb.org/t/p";

    private static String imageSize = "w185";

    public ImageAdapter(Context c, List<Movie> moviesList) {
        super(c, 0, moviesList);
        mContext = c;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parentViewGroup) {
        Movie currentMovie = getItem(pos);
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_movie_poster, parentViewGroup, false);
        }

        String fullImagePath = Uri.parse(base_path).buildUpon().appendPath(imageSize).appendPath(currentMovie.getPoster_path()).build().toString();

        ImageView currentItemImageView = (ImageView) convertView.findViewById(R.id.list_item_movie_poster_image);
        Picasso.with(mContext).load(fullImagePath).resize(200, 250).into(currentItemImageView);

        return convertView;
    }

}
