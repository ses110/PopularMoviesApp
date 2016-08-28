package com.example.sergioescoto.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sergioescoto on 6/12/16.
 */
public class Movie implements Parcelable {

    private String poster_path;
    private Boolean adult;
    private String overview;
    private String release_date;
    private int[] genre_ids;
    private int id;
    private String original_title;
    private String original_language;
    private String title;
    private String backdrop_path;
    private int popularity;
    private int vote_count;
    private boolean video;
    private Double vote_average;

    public Movie() {
    }

    protected Movie(Parcel in) {
        poster_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
        genre_ids = in.createIntArray();
        id = in.readInt();
        original_title = in.readString();
        original_language = in.readString();
        title = in.readString();
        backdrop_path = in.readString();
        popularity = in.readInt();
        vote_count = in.readInt();
        video = in.readByte() != 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getMovieTitle() {
        return this.original_title;
    }

    public int[] getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(int[] genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    @Override
    public String toString() {
        StringBuffer movieString = new StringBuffer();
        movieString.append("\ntitle: ");
        movieString.append(title);
        movieString.append("\nposter_path: ");
        movieString.append(poster_path);
        movieString.append("\nadult: ");
        movieString.append(adult);
        movieString.append("\noverview: ");
        movieString.append(overview);
        movieString.append("\nrelease_date: ");
        movieString.append(release_date);
        movieString.append("\ngenre_ids: ");
        for(int i = 0; i < genre_ids.length; i++) {
            movieString.append(this.genre_ids[i]);
            if(i != genre_ids.length - 1) {
                movieString.append(", ");
            }
        }
        movieString.append("\nid: ");
        movieString.append(id);
        movieString.append("\noriginal_title: ");
        movieString.append(original_title);
        movieString.append("\noriginal_language: ");
        movieString.append(original_language);
        movieString.append("\nbackdrop_path: ");
        movieString.append(backdrop_path);
        movieString.append("\npopularity: ");
        movieString.append(popularity);
        movieString.append("\nvote_count: ");
        movieString.append(vote_count);
        movieString.append("\nvideo: ");
        movieString.append(video);
        movieString.append("\nvote_average: ");
        movieString.append(vote_average);

        return movieString.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(poster_path);
        parcel.writeString(overview);
        parcel.writeString(release_date);
        parcel.writeIntArray(genre_ids);
        parcel.writeInt(id);
        parcel.writeString(original_title);
        parcel.writeString(original_language);
        parcel.writeString(title);
        parcel.writeString(backdrop_path);
        parcel.writeInt(popularity);
        parcel.writeInt(vote_count);
        parcel.writeByte((byte) (video ? 1 : 0));
    }
}
