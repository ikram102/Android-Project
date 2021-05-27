package com.android.movies.utils;


import android.content.Context;
import android.widget.Toast;

public class Constants {

    public static final String IMAGE_URL_BASE_PATH = "http://image.tmdb.org/t/p/w342";
    public static final String STORED_MOVIES = "StoredMovies";

    public static final void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}
